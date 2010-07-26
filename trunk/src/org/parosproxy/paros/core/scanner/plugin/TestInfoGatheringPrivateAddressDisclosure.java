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

import org.parosproxy.paros.core.scanner.AbstractAppPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;

public class TestInfoGatheringPrivateAddressDisclosure extends
		AbstractAppPlugin {

	public static final Pattern patternPrivateIP = Pattern.compile(
		"(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|172\\.\\d{2,2}\\.\\d{1,3}\\.\\d{1,3}|192\\.168\\.\\d{1,3}\\.\\d{1,3})",
		PATTERN_PARAM);

	public int getId() {
		return 00001;
	}

	public String getName() {
		return "Private IP disclosure";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		String msg = "Private IPs such as 10.x.x.x, 172.x.x.x, 192.168.x.x "
			+ "have been found in the HTTP response body. This enables an "
			+ "attacker to get conclusion about the internal network "
			+ "architecture and might be used for exploiting internal systems.";
		return msg;
	}

	public int getCategory() {
		return Category.INFO_GATHER;
	}

	public String getSolution() {
		String msg = "Remove private IP addresses from HTTP response bodies. "
			+ "For comments, use JSP/ASP comments instead of HTML/JavaScript "
			+ "comments which are disclosed to client browsers.";
		return msg;
	}

	public String getReference() {
		return null;
	}

	public void init() {

	}

	public void scan() {

		HttpMessage msg = getBaseMsg();
		String txtBody = msg.getResponseBody().toString();
		String txtFound = null;
		Matcher matcher = patternPrivateIP.matcher(txtBody);
		while (matcher.find()) {
			txtFound = matcher.group();
			if (txtFound != null) {
				bingo(Alert.RISK_LOW, Alert.WARNING, null, null, txtFound, msg);
			}
		}
	}

}
