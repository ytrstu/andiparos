
package org.parosproxy.paros.core.scanner.plugin;

import org.parosproxy.paros.core.scanner.AbstractDefaultFilePlugin;
import org.parosproxy.paros.core.scanner.Category;

public class TestInformationDislosureRobotsTxt extends AbstractDefaultFilePlugin {

	public int getId() {
		return 10001;
	}

	public String getName() {
		return "robots.txt file";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		return "Potential path disclosure through robots.txt file.";
	}

	public int getCategory() {
		return Category.INFO_DISCLOSURE;
	}

	public String getSolution() {
		return "Do not disallow server paths directly. Use wildcard (*) instead in order "
			+ "to not disclose sensitive server paths.";
	}

	public String getReference() {
		return "";
	}

	public void init() {
		super.init();
		createURI();
	}

	private void createURI() {
		addTest("/","robots.txt");
	}
}
