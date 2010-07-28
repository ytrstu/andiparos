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

public class TestDefaultFileTomcat extends AbstractDefaultFilePlugin {

	public int getId() {
		return 30007;
	}

	public String getName() {

		return "Tomcat source file disclosure";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		return "Tomcat source file disclosed.";
	}

	public int getCategory() {
		return Category.SERVER;
	}

	public String getSolution() {
		return "Upgrade to newer version.";
	}

	public String getReference() {
		return "";
	}

	public void init() {
		super.init();
		createURI();
	}

	private void createURI() {

		addTest("", "\u0000.jsp"); // source exposure for Tomcat 3.3.1

	}

}
