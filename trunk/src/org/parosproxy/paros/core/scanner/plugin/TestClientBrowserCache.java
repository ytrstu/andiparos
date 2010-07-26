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

import org.parosproxy.paros.core.scanner.AbstractAppPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class TestClientBrowserCache extends AbstractAppPlugin {

	public final static Pattern patternNoCache = Pattern.compile(
		"\\QNo-cache\\E|\\QNo-store\\E", PATTERN_PARAM);

	// <meta http-equiv="Pragma" content="no-cache">
	// <meta http-equiv="Cache-Control" content="no-cache">
	public final static Pattern patternHtmlNoCache = Pattern.compile(
		"<META[^>]+(Pragma|\\QCache-Control\\E)[^>]+(\\QNo-cache\\E|\\QNo-store\\E)[^>]*>", PATTERN_PARAM);
	
	public int getId() {
		return 20001;
	}

	public String getName() {
		return "Secure page browser cache";
	}

	public String[] getDependency() {
		return null;
	}

	
	public String getDescription() {
		String msg = "Secure pages can be cached by the browser. Appropriate cache control settings are "
			+ "neither set in the HTTP header nor in the HTML header. Therefore, sensitive content can be "
			+ "recovered from browser's cache.";
		return msg;
	}

	public int getCategory() {
		return Category.BROWSER;
	}

	public String getSolution() {
		String msg = "Set a HTTP header with: 'Cache-control: no-cache, no-store'. Additionally, "
			+ "but not preferably as the one and only solution you can set the HTML headers: "
			+ "\n<META HTTP-EQUIV='Pragma' CONTENT='no-cache'>"
			+ "\n<META HTTP-EQUIV='Cache-Control' CONTENT='no-cache'>";
		return msg;
	}


	public String getReference() {
		String msg = "How to prevent caching in Internet Explorer: "
			+ "http://support.microsoft.com/default.aspx?kbid=234067 "
			+ "\n\"Pragma: No-cache\" Tag May Not Prevent Page from Being Cached: "
			+ "http://support.microsoft.com/default.aspx?kbid=222064";
		return msg;
	}

	public void init() {

	}

	public void scan() {

		HttpMessage msg = getBaseMsg();
		boolean result = false;

		if (!msg.getRequestHeader().getSecure()) {
			// no need to if non-secure page;
			return;
		} else if (msg.getRequestHeader().isImage()) {
			// does not bother if image is cached
			return;
		} else if (msg.getResponseBody().length() == 0) {
			return;
		}

		if (!matchHeaderPattern(msg, HttpHeader.CACHE_CONTROL, patternNoCache)
				&& !matchHeaderPattern(msg, HttpHeader.PRAGMA, patternNoCache)
				&& !matchBodyPattern(msg, patternHtmlNoCache, null)) {

			result = true;
		}

		if (result) {
			bingo(Alert.RISK_MEDIUM, Alert.WARNING, null, null, "", msg);
		}

	}

}
