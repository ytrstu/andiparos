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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.URIException;
import org.parosproxy.paros.core.scanner.AbstractAppPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;

public class TestInfoGatheringSessionIdURL extends AbstractAppPlugin {

	private static Pattern staticSessionIDPHP1 = Pattern.compile(
			"(PHPSESSION)=\\w+", PATTERN_PARAM);
	private static Pattern staticSessionIDPHP2 = Pattern.compile(
			"(PHPSESSID)=\\w+", PATTERN_PARAM);
	private static Pattern staticSessionIDJava = Pattern.compile(
			"(JSESSIONID)=\\w+", PATTERN_PARAM);
	private static Pattern staticSessionIDASP = Pattern.compile(
			"(ASPSESSIONID)=\\w+", PATTERN_PARAM);
	private static Pattern staticSessionIDColdFusion = Pattern.compile(
			"(CFTOKEN)=\\w+", PATTERN_PARAM);
	private static Pattern staticSessionIDJW = Pattern.compile(
			"(JWSESSIONID)=\\w+", PATTERN_PARAM);
	private static Pattern staticSessionIDWebLogic = Pattern.compile(
			"(WebLogicSession)=\\w+", PATTERN_PARAM);
	private static Pattern staticSessionIDApache = Pattern.compile(
			"(SESSIONID)=\\w+", PATTERN_PARAM);

	private static Pattern[] staticSessionIDList = {
		staticSessionIDPHP1,
		staticSessionIDPHP2,
		staticSessionIDJava,
		staticSessionIDColdFusion,
		staticSessionIDASP,
		staticSessionIDJW,
		staticSessionIDWebLogic,
		staticSessionIDApache
	};

	public int getId() {
		return 00002;
	}

	public String getName() {
		return "Session ID in URL rewrite";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		String msg = "URL rewrite is used to track user session ID. "
			+ "The session ID may be disclosed in a referrer header. "
			+ "Besides, the session ID can be stored in browser history, "
			+ "proxy and server logs.";
		return msg;
	}

	public int getCategory() {
		return Category.INFO_GATHER;
	}

	public String getSolution() {
		String msg = "For secure content, put the session ID into a cookie.";
		return msg;
	}


	public String getReference() {
		return "http://seclists.org/lists/webappsec/2002/Oct-Dec/0111.html";
	}

	public void init() {

	}

	public void scan() {
		HttpMessage base = getBaseMsg();

		String uri = base.getRequestHeader().getURI().toString();
		Matcher matcher = null;
		String sessionIdValue = null;
		String sessionIdName = null;
		for (int i = 0; i < staticSessionIDList.length; i++) {
			matcher = staticSessionIDList[i].matcher(uri);
			if (matcher.find()) {
				sessionIdValue = matcher.group(0);
				sessionIdName = matcher.group(1);
				String kb = getKb().getString("sessionId/nameValue");

				if (kb == null || !kb.equals(sessionIdValue)) {
					getKb().add("sessionId/nameValue", sessionIdValue);
					bingo(Alert.RISK_LOW, Alert.WARNING, null, "", sessionIdValue, base);
				}
				kb = getKb().getString("sessionId/name");
				getKb().add("sessionId/name", sessionIdName);
				try {
					checkSessionIDExposure(base);
				} catch (URIException e) {
				}
				break;
			}
		}

	}

	private static final String paramHostHttp = "http://([\\w\\.\\-_]+)";
	private static final String paramHostHttps = "https://([\\w\\.\\-_]+)";
	private static final Pattern[] staticLinkCheck = {
			Pattern.compile("src\\s*=\\s*\"?" + paramHostHttp, PATTERN_PARAM),
			Pattern.compile("href\\s*=\\s*\"?" + paramHostHttp, PATTERN_PARAM),
			Pattern.compile("src\\s*=\\s*\"?" + paramHostHttps, PATTERN_PARAM),
			Pattern.compile("href\\s*=\\s*\"?" + paramHostHttps, PATTERN_PARAM),

	};

	private static final String alertReferer = "Referrer exposes session ID";
	private static final String descReferer = "Hyperlink to other hostname found. As session ID URL "
		+ "rewrite is used, it may be disclosed in referrer header to external host.";
	private static final String solutionReferer = "This is a risk if the session ID is sensitive and "
		+ "the hyperlink refers to an external host. For secure content, put the session ID in a secure "
		+ "session cookie.";

	private void checkSessionIDExposure(HttpMessage msg) throws URIException {

		String body = msg.getResponseBody().toString();
		int risk = (msg.getRequestHeader().getSecure()) ? Alert.RISK_MEDIUM : Alert.RISK_INFO;
		String linkHostName = null;
		Matcher matcher = null;

		for (int i = 0; i < staticLinkCheck.length; i++) {
			matcher = staticLinkCheck[i].matcher(body);

			while (matcher.find()) {
				linkHostName = matcher.group(1);
				String host = msg.getRequestHeader().getURI().getHost();
				if (host.compareToIgnoreCase(linkHostName) != 0) {
					bingo(risk, Alert.WARNING, alertReferer, descReferer, null,
							null, linkHostName, solutionReferer, msg);
				}
			}
		}
	}

}
