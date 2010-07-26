/*
 * Copyright (C) 2010, Andiparos Project, Axel Neumann
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

import org.parosproxy.paros.core.scanner.AbstractDefaultFilePlugin;
import org.parosproxy.paros.core.scanner.Category;

public class TestDefaultFileCognos extends AbstractDefaultFilePlugin {

	public int getId() {
		return 30001;
	}

	public String getName() {

		return "Cognos default files";
	}

	public String[] getDependency() {
		return null;
	}

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
