package org.parosproxy.paros.core.scanner.plugin;


import org.apache.commons.httpclient.URI;
import org.parosproxy.paros.core.scanner.AbstractAppPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;

public class TestCrossSiteScriptInPath extends AbstractAppPlugin {

	private static final String XSS = "<SCRIPT>alert(\"XSS\");</SCRIPT>";

	public int getId() {
		return 40006;
	}

	public String getName() {
		return "Cross site scripting in path";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		return "XSS in path is possible";
	}

	public String getSolution() {
		return "Check all entries from the client";
	}

	public String getReference() {
		return "http://www.cgisecurity.org/articles/xss-faq.shtml";
	}

	public int getCategory() {
		return Category.HTML_INJECTION;
	}

	public void init() {

	}

	public void scan() {
		String result = null;
		String path = null;
		URI uri = null;
		int pos = 0;
		HttpMessage msg = getNewMsg();
		try {
			uri = msg.getRequestHeader().getURI();
			path = uri.getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (path == null || path.equals("")) {
			return;
		}

		msg = getNewMsg();
		String newPath = path + "/" + XSS;
		try {
			uri.setPath(newPath);
			msg.getRequestHeader().setURI(uri);
			sendAndReceive(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		result = msg.getResponseBody().toString();
		pos = result.indexOf(XSS);

		if (pos == -1)
			return;

		bingo(Alert.RISK_MEDIUM, Alert.WARNING, uri.toString(), "", "", msg);
	}
}