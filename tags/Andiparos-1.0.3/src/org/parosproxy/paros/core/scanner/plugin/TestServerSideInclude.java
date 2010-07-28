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

import java.util.regex.Pattern;

import org.parosproxy.paros.core.scanner.AbstractAppParamPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;

public class TestServerSideInclude extends AbstractAppParamPlugin {

	private static final String SSI_UNIX = "<!--#EXEC cmd=\"ls /\"-->";
	private static final String SSI_UNIX2 = "\">" + SSI_UNIX + "<";
	private static final String SSI_WIN = "<!--#EXEC cmd=\"dir \\\"-->";
	private static final String SSI_WIN2 = "\">" + SSI_WIN + "<";

	private static Pattern patternSSIUnix = Pattern.compile(
			"\\broot\\b.*\\busr\\b", PATTERN_PARAM);
	private static Pattern patternSSIWin = Pattern.compile(
			"\\bprogram files\\b.*\\b(WINDOWS|WINNT)\\b", PATTERN_PARAM);

	public int getId() {
		return 40004;
	}

	public String getName() {
		return "Server Side Include";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		String msg = "Certain parameters may cause Server Side Include commands to be executed.  This may allow database connection or arbitrary code to be executed.";
		return msg;
	}

	public int getCategory() {
		return Category.HTML_INJECTION;
	}

	public String getSolution() {
		String msg = "Do not trust client side input and enforece tight check in the server side. "
			+ "Disable server side include. Refer to manual to disable Sever Side Include. Use least "
			+ "privilege to run your web server or application server.\n"
			+ "For Apache, disable the following:\n"
			+ "Options Indexes FollowSymLinks Includes\n"
			+ "AddType application/x-httpd-cgi .cgi\n"
			+ "AddType text/x-server-parsed-html .html";
		return msg;
	}

	public String getReference() {
		String msg = "http://www.carleton.ca/~dmcfet/html/ssi.html";
		return msg;
	}


	public void init() {

	}

	public void scan(HttpMessage msg, String param, String value) {

		try {
			setParameter(msg, param, SSI_UNIX);
			sendAndReceive(msg);
			
			if (matchBodyPattern(msg, patternSSIUnix, null)) {
				bingo(Alert.RISK_HIGH, Alert.WARNING, null, param, null, msg);
				return;
			}

		} catch (Exception e) {
		}

		try {
			msg = getNewMsg();
			setParameter(msg, param, SSI_UNIX2);
			sendAndReceive(msg);
			
			if (matchBodyPattern(msg, patternSSIUnix, null)) {
				bingo(Alert.RISK_HIGH, Alert.WARNING, null, param, null, msg);
				return;
			}

		} catch (Exception e) {
		}

		try {
			msg = getNewMsg();
			setParameter(msg, param, SSI_WIN);
			sendAndReceive(msg);
			
			if (matchBodyPattern(msg, patternSSIWin, null)) {
				bingo(Alert.RISK_HIGH, Alert.WARNING, null, param, null, msg);
				return;
			}

		} catch (Exception e) {
		}

		try {
			msg = getNewMsg();
			setParameter(msg, param, SSI_WIN2);
			sendAndReceive(msg);
			
			if (matchBodyPattern(msg, patternSSIWin, null)) {
				bingo(Alert.RISK_HIGH, Alert.WARNING, null, param, null, msg);
				return;
			}

		} catch (Exception e) {
		}

	}

}
