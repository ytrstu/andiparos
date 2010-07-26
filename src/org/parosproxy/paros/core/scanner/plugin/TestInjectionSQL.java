/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.parosproxy.paros.core.scanner.plugin;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.parosproxy.paros.core.scanner.AbstractAppParamPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpStatusCode;

public class TestInjectionSQL extends AbstractAppParamPlugin {

	private static final String[] dependency = { "TestInjectionSQLFingerprint" };

	private static final int TIME_SPREAD = 5000;

	private static final String SQL_DELAY_1 = "';waitfor delay '0:0:05';--";
	private static final String SQL_DELAY_2 = ";waitfor delay '0:0:05';--";
	private static final String SQL_BLIND_MS_INSERT = ");waitfor delay '0:0:05';--";

	private static final String[] SQL_AND = { " AND 1=1", "' AND '1'='1", "\" AND \"1\"=\"1", }; 
	private static final String[] SQL_AND_ERR = { " AND 1=2", "' AND '1'='2", "\" AND \"1\"=\"2" }; 
	private static final String[] SQL_OR = { " OR 1=1", "' OR '1'='1", "\" OR \"1\"=\"1", }; 

	private static final String SQL_CHECK_ERR = "'INJECTED_PARAM"; 

	private static final Pattern patternErrorODBC1 = Pattern.compile("Microsoft OLE DB Provider for ODBC Drivers.*error", PATTERN_PARAM);
	private static final Pattern patternErrorODBC2 = Pattern.compile("ODBC.*Drivers.*error", PATTERN_PARAM);
	private static final Pattern patternErrorGeneric = Pattern.compile("JDBC|ODBC|not a valid MySQL|SQL", PATTERN_PARAM);
	private static final Pattern patternErrorODBCMSSQL = Pattern.compile("ODBC SQL Server Driver", PATTERN_PARAM);

	private String mResBodyNormal = ""; // normal response for comparison

	public int getId() {
		return 50001;
	}

	public String getName() {
		return "SQL Injection";
	}

	public String[] getDependency() {

		return dependency;
	}

	public String getDescription() {
		String msg = "SQL injection is possible.  User parameters submitted will be formulated into a SQL query for database processing.  "
				+ "If the query is built by simple 'string concatenation', it is possible to modify the meaning of the query by carefully crafting the parameters.  "
				+ "Depending on the access right and type of database used, tampered query can be used to retrieve sensitive information from the database or execute arbitrary code.  "
				+ "MS SQL and PostGreSQL, which supports multiple statements, may be exploited if the database access right is more powerful.\r\n"
				+ "This can occur in URL query strings, POST paramters or even cookies.  Currently check on cookie is not supported by Paros.  "
				+ "You should check SQL injection manually as well as some blind SQL injection areas cannot be discovered by this check.";
		return msg;
	}

	public int getCategory() {
		return Category.SQL_INJECTION;
	}

	public String getSolution() {
		String msg = "Do not trust client side input even if there is client side validation.  In general, "
				+ "<ul>"
				+ "<li>If the input string is numeric, type check it.</li>"
				+ "<li>If the application used JDBC, use PreparedStatement or CallableStatement with parameters passed by '?'</li>"
				+ "<li>If the application used ASP, use ADO Command Objects with strong type checking and parameterized query.</li>"
				+ "<li>If stored procedure or bind variables can be used, use it for parameter passing into query.  Do not just concatenate string into query in the stored procedure!</li>"
				+ "<li>Do not create dynamic SQL query by simple string concatentation.</li>"
				+ "<li>Use minimum database user privilege for the application.  This does not eliminate SQL injection but minimize its damage.  "
				+ "Eg if the application require reading one table only, grant such access to the application.  Avoid using 'sa' or 'db-owner'.</li>" + "</ul>";
		return msg;
	}

	public String getReference() {
		String msg = "<ul><li>The OWASP guide at http://www.owasp.org/documentation/guide</li>" + "<li>http://www.sqlsecurity.com/DesktopDefault.aspx?tabid=23</li>"
				+ "<li>http://www.spidynamics.com/whitepapers/WhitepaperSQLInjection.pdf</li>"
				+ "<li>For Oracle database, refer to http://www.integrigy.com/info/IntegrigyIntrotoSQLInjectionAttacks.pdf</li></ul>";
		return msg;
	}

	public void init() {

	}

	public void scan(HttpMessage baseMsg, String param, String value) {
		try {
			scanSQL(baseMsg, param, value);
		} catch (Exception e) {

		}
	}

	
	public void scanSQL(HttpMessage baseMsg, String param, String value) throws HttpException, IOException {

		String bingoQuery = null;
		String displayURI = null;
		String newQuery = null;

		String resBodyAND = null;
		String resBodyANDErr = null;
		String resBodyOR = null;

		long defaultTimeUsed = 0;

		HttpMessage msg = getNewMsg();

		// always try normal query first
		sendAndReceive(msg);
		defaultTimeUsed = msg.getTimeElapsedMillis();
		if (msg.getResponseHeader().getStatusCode() != HttpStatusCode.OK) {
			return;
		}

		mResBodyNormal = msg.getResponseBody().toString();

		// 2nd try an always error SQL query

		newQuery = setParameter(msg, param, value + SQL_CHECK_ERR);
		sendAndReceive(msg);

		if (checkANDResult(msg, newQuery)) {
			return;
		}

		// blind sql injections

		for (int i = 0; i < SQL_AND.length; i++) {
			bingoQuery = setParameter(msg, param, value + SQL_AND[i]);
			sendAndReceive(msg);

			displayURI = msg.getRequestHeader().getURI().toString();

			if (checkANDResult(msg, bingoQuery)) {
				return;
			}

			if (msg.getResponseHeader().getStatusCode() == HttpStatusCode.OK) {

				resBodyAND = stripOff(msg.getResponseBody().toString(), SQL_AND[i]);

				if (resBodyAND.compareTo(mResBodyNormal) == 0) {

					newQuery = setParameter(msg, param, value + SQL_AND_ERR[i]);
					sendAndReceive(msg);
					resBodyANDErr = stripOff(msg.getResponseBody().toString(), SQL_AND_ERR[i]);

					// build a always false AND query. Result should be
					// different to prove the SQL works.
					if (resBodyANDErr.compareTo(mResBodyNormal) != 0) {
						getKb().add(msg.getRequestHeader().getURI(), "sql/and", new Boolean(true));
						bingo(Alert.RISK_HIGH, Alert.WARNING, displayURI, bingoQuery, "", msg);
						return;
					} else {
						// OR check is used to figure out if there is any
						// diffrence if a AND query return nothing
						newQuery = setParameter(msg, param, value + SQL_OR[i]);
						sendAndReceive(msg);
						resBodyOR = stripOff(msg.getResponseBody().toString(), SQL_OR[i]);

						if (resBodyOR.compareTo(mResBodyNormal) != 0) {
							getKb().add(msg.getRequestHeader().getURI(), "sql/or", new Boolean(true));
							bingo(Alert.RISK_HIGH, Alert.WARNING, displayURI, newQuery, "", msg);
							return;
						}
					}
				}

			}
		}

		if (getKb().getBoolean(msg.getRequestHeader().getURI(), "sql/mssql")) {
			return;
		}

		// try BLIND SQL SELECT using timing
		newQuery = setParameter(msg, param, value + SQL_DELAY_1);
		sendAndReceive(msg);

		if (checkTimeResult(msg, newQuery, defaultTimeUsed, msg.getTimeElapsedMillis())) {
			return;
		}

		newQuery = setParameter(msg, param, value + SQL_DELAY_2);
		sendAndReceive(msg);

		if (checkTimeResult(msg, newQuery, defaultTimeUsed, msg.getTimeElapsedMillis())) {
			return;
		}

		// try BLIND MSSQL INSERT using timing

		testBlindINSERT(msg, param, value);

	}

	private void testBlindINSERT(HttpMessage msg, String param, String value) throws HttpException, IOException {
		String newQuery = null;

		long defaultTimeUsed = 0;

		int TRY_COUNT = 5;
		StringBuffer sbInsertValue = new StringBuffer();

		// try insert param using INSERT and timing
		sbInsertValue = new StringBuffer();
		for (int i = 0; i < TRY_COUNT; i++) {

			if (i > 0) {
				sbInsertValue.append(",'0'");
			}

			// try INSERT
			newQuery = setParameter(msg, param, value + "'" + sbInsertValue.toString() + SQL_BLIND_MS_INSERT);

			sendAndReceive(msg);

			if (checkTimeResult(msg, newQuery, defaultTimeUsed, msg.getTimeElapsedMillis())) {
				getKb().add(msg.getRequestHeader().getURI(), "sql/mssql", new Boolean(true));
				return;
			}

			// no need to try following if not a value integer
			try {
				Long.parseLong(value);
			} catch (NumberFormatException e) {
				continue;
			}
			newQuery = setParameter(msg, param, value + sbInsertValue.toString() + SQL_BLIND_MS_INSERT);
			sendAndReceive(msg);

			if (checkTimeResult(msg, newQuery, defaultTimeUsed, msg.getTimeElapsedMillis())) {
				getKb().add(msg.getRequestHeader().getURI(), "sql/mssql", new Boolean(true));
				return;
			}

		}

	}

	private boolean checkANDResult(HttpMessage msg, String query) {

		StringBuffer sb = new StringBuffer();

		if (msg.getResponseHeader().getStatusCode() != HttpStatusCode.OK && !HttpStatusCode.isServerError(msg.getResponseHeader().getStatusCode())) {
			return false;
		}

		if (matchBodyPattern(msg, patternErrorODBCMSSQL, sb)) {
			getKb().add(msg.getRequestHeader().getURI(), "sql/mssql", new Boolean(true));
		}

		if (matchBodyPattern(msg, patternErrorODBC1, sb) || matchBodyPattern(msg, patternErrorODBC2, sb)) {
			// check for ODBC error. Almost certain.
			bingo(Alert.RISK_HIGH, Alert.WARNING, null, query, sb.toString(), msg);
			return true;
		} else if (matchBodyPattern(msg, patternErrorGeneric, sb)) {
			// check for other sql error (JDBC) etc. Suspicious.
			bingo(Alert.RISK_HIGH, Alert.SUSPICIOUS, null, query, sb.toString(), msg);
			return true;
		}

		return false;

	}

	private boolean checkTimeResult(HttpMessage msg, String query, long defaultTimeUsed, long timeUsed) {

		boolean result = checkANDResult(msg, query);
		if (result) {
			return result;
		}

		if (timeUsed > defaultTimeUsed + TIME_SPREAD - 500) {
			// allow 500ms discrepancy
			bingo(Alert.RISK_HIGH, Alert.SUSPICIOUS, null, query, "", msg);
			return true;
		}
		return false;
	}

}
