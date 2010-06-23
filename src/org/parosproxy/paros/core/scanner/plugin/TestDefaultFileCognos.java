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

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class TestDefaultFileCognos extends AbstractDefaultFilePlugin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.proofsecure.paros.core.scanner.Test#getId()
	 */
	public int getId() {
		return 20008;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.proofsecure.paros.core.scanner.Test#getName()
	 */
	public String getName() {

		return "Cognos default files";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.proofsecure.paros.core.scanner.Test#getDependency()
	 */
	public String[] getDependency() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.proofsecure.paros.core.scanner.Test#getSummary()
	 */
	public String getDescription() {
		return "Cognos default files have been found.";
	}

	public int getCategory() {
		return Category.SERVER;
	}

	public String getSolution() {
		return "Check if there is a disclosure of confidential information.";
	}

	public String getReference() {
		return "";
	}

	public void init() {
		super.init();
		createURI();
	}

	private void createURI() {

		addTest("/ppwb/Temp", "/");
		addTest("/cgi-bin", "ppdscgi.exe?ABOUT=");
		addTest("/cgi-bin", "ppdscgi.exe?TOC=");
		addTest("/cognos/cgi-bin",
				"upfcgi.exe?xmlcmd=<GetPage><Template>shoucmp.utml</Template></GetPage>");
		addTest("/cognos/cgi-bin",
				"upfcgi.exe?xmlcmd=<GetPage><Template>shoglbvar.utml</Template></GetPage>");
		addTest("/cognos/cgi-bin", "ppdscgi.exe");
		addTest("/cognos/cgi-bin", "imrap.cgi");
		addTest("/cognos/cgi-bin", "cqcgi.exe");
		addTest("/cognos/cgi-bin", "vizcgi.exe");

	}

}
