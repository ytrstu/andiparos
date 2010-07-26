/*
 * Copyright (C) 2010, Andiparos Project
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/copyleft/
 * 
 */

package org.parosproxy.paros.core.scanner.plugin;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.URI;
import org.parosproxy.paros.core.scanner.AbstractAppPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpStatusCode;

public class TestInformationDisclosurePhpInfo extends AbstractAppPlugin {

	private final static Pattern patternPhpInfo = Pattern.compile(
			"PHP Version", PATTERN_PARAM);

	private final static String[] fileList = { "phpinfo.php", "info.php" };

	public int getId() {
		return 10002;
	}

	public String getName() {
		return "phpinfo file";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		return "Disclosure of the used environment options via phpinfo.";
	}

	public String getSolution() {
		return "Remove php files containing the phpinfo() function.";
	}

	public String getReference() {
		return "";
	}

	public int getCategory() {
		return Category.INFO_DISCLOSURE;
	}

	public void init() {

	}

	public void scan() {
		for (int i = 0; i < fileList.length; i++) {
			try {
				testFile(fileList[i]);
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Test existence of file.
	 * 
	 * @param fileName
	 *            to run scan with.
	 */
	private void testFile(String fileName) throws IOException {

		boolean suspiciousFileFound = false;
		HttpMessage msg = getNewMsg();

		try {
			URI uri = msg.getRequestHeader().getURI();
			String path = uri.getPath();

			if (path == null || path.equals("")) {
				return;
			}
			
			
			if (!path.endsWith("/")) {
				path = path + "/";
			}

			path = path + fileName;

			uri.setPath(path);
			msg.getRequestHeader().setURI(uri);

			sendAndReceive(msg);

			if (msg.getResponseHeader().getStatusCode() != HttpStatusCode.OK) {
				return;
			}

			if (matchBodyPattern(msg, patternPhpInfo, null)) {
				suspiciousFileFound = true;
			}
		} catch (IOException e) {
		}

		if (suspiciousFileFound) {
			bingo(Alert.RISK_MEDIUM, Alert.WARNING, msg.getRequestHeader()
					.getURI().toString(), "", "", msg);
		}
	}
}