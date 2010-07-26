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

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.core.scanner.AbstractAppParamPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;

public class TestInfoGatheringPotentialPathManipulation extends
		AbstractAppParamPlugin {

	private static Pattern[] patternFilePath = {
			Pattern.compile("\\A((/)[\\w\\.\\-~]+)+(/)?\\z", PATTERN_PARAM),
			Pattern.compile("\\A[\\w\\.\\-~]+((/)[\\w\\.\\-~]+)+\\z", PATTERN_PARAM),
			Pattern.compile("\\A((\\\\)[\\w\\.\\-~]+)+(\\\\)?\\z", PATTERN_PARAM),
			Pattern.compile("\\A[\\w\\.\\-]+((\\\\)[\\w\\.\\-~]+)+\\z", PATTERN_PARAM)

	};

	public int getId() {
		return 00000;
	}

	public String getName() {
		return "Potential File Path Manipulation";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {

		String msg = "Possibly there is a file path in the parameter. "
				+ "You should try to manipulate manually in order to "
				+ "detect a potential information exposure such as system "
				+ "files or program source code. In that case this would "
				+ "be a high risk vulnerability.";
		return msg;
	}

	public int getCategory() {
		return Category.INFO_GATHER;
	}

	public String getSolution() {
		String msg = "Make sure the file path parameter cannot be manipulated "
			+ "to read arbitrary files. Restrict access to intended files only.";
		return msg;
	}

	public String getReference() {
		return "";

	}

	public void init() {

	}

	public void scan(HttpMessage msg, String param, String value) {

		String query = setParameter(msg, param, value);
		String matchedFilePath = null;
		Matcher matcher = null;

		for (int i = 0; i < patternFilePath.length; i++) {
			matcher = patternFilePath[i].matcher(value);
			if (matcher.find()) {
				matchedFilePath = matcher.group(0);
				bingo(Alert.RISK_INFO, Alert.SUSPICIOUS, "",
					(query == null || query.length() == 0) ? "nil" : query, matchedFilePath, msg);
				return;
			}
		}
	}

	public boolean isVisible() {
		return Constant.isSP();
	}

}
