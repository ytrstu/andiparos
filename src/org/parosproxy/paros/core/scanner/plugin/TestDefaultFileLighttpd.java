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

import org.parosproxy.paros.core.scanner.AbstractDefaultFilePlugin;
import org.parosproxy.paros.core.scanner.Category;

public class TestDefaultFileLighttpd extends AbstractDefaultFilePlugin {

	public int getId() {
		return 20009;
	}

	public String getName() {
		return "Lighttpd default files";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		return "Lighttpd 1.4.x default files have been found.";
	}

	public int getCategory() {
		return Category.SERVER;
	}

	public String getSolution() {
		return "Remove default files and virtual directories.";
	}

	public String getReference() {
		return "";
	}

	public void init() {
		super.init();
		createURI();
	}

	private void createURI() {

		addTest(
				"/",
				"cgi-pathinfo.pl,cgi.php,cgi.pl,exec-date.shtml,get-env.php,get-header.pl,get-post-len.pl,get-server-env.php,nph-status.pl,prefix.fcgi,redirect.php,ssi.shtml");
		addTest("/dummydir", "");
		addTest("/expire", "access.txt,modification.txt");
		addTest("/go", "cgi.php");
		addTest("/indexfile", "index.php,return-404.php,rewrite.php");
		addTest("/", "12345.html,12345.txt,dummyfile.bla");

	}
}
