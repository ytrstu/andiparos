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
package org.parosproxy.paros.core.scanner;

/**
 * 00000 - 09999 : Information gathering
 * 10000 - 19999 : Information disclosure
 * 20000 - 29999 : Client browser
 * 30000 - 39999 : Server security (Default files)
 * 40000 - 49999 : HTML injection
 * 50000 - 59999 : SQL injection
 * 60000 - 69999 : Miscellaneous
 */

public class Category {
	public static final int INFO_GATHER = 0;
	public static final int INFO_DISCLOSURE = 1;
	public static final int BROWSER = 2;
	public static final int SERVER = 3;
	public static final int HTML_INJECTION = 4;
	public static final int SQL_INJECTION = 5;
	public static final int MISC = 6;


	private static String[] names = {
		"Information gathering",
		"Information disclosure",
		"Client browser",
		"Server security",
		"HTML injection",
		"SQL injection",
		"Miscellaneous"
	};

	public static String getName(int category) {
		String result = "Undefined";
		if (category < names.length) {
			result = names[category];
		}
		return result;
	}

	public static int getCategory(String name) {
		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase(name)) {
				return i;
			}
		}
		return 0;
	}

	public static String[] getAllNames() {
		return names;
	}

	public static int length() {
		return names.length;
	}
}
