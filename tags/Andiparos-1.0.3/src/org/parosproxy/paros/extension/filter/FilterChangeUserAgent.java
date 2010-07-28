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
package org.parosproxy.paros.extension.filter;

import javax.swing.JOptionPane;

import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class FilterChangeUserAgent extends FilterAdaptor {
	static final String[] userAgentName = {
		"Firefox 3.5.6 Windows XP",
    	"Firefox 3.0.5 Windows XP",
    	"Firefox 3.0.5 MacOSX Intel",
    	"Firefox 2.0.0.6 Windows XP",
		"Firefox 1.0.1 Windows XP",
        "Firefox 1.0 Windows 2000",
		"Internet Explorer 8.0 Windows XP",
        "Internet Explorer 7.0 Windows Vista",
        "Internet Explorer 7.0 Windows XP",
        "Internet Explorer 6.0 Windows XP",
        "Internet Explorer 6.0 Windows 2000",
        "Internet Explorer 5.5 Windows XP",
        "Internet Explorer 5.5 Windows 2000",
        "Internet Explorer 5.0 Windows XP",
        "Internet Explorer 5.0 Windows 2000",
        "Netscape 7.2 Widows XP",
        "Safari 3.2.1 Apple MacOSX Intel",
        "Opera 9.63 Windows XP English",
        "Opera 7.0 Windows XP English",
        "Opera 6.0 Windows XP English"
    };
    
    private static final String[] userAgentHeader = {
    	"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6",
    	"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5",
    	"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.5) Gecko/2008120121 Firefox/3.0.5",
		"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7",
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6) Gecko/20050223 Firefox/1.0.1",
        "Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.7.5) Gecko/20041107 Firefox/1.0",
		"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1)",
        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)",
        "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.1)",
        "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)",
        "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT 5.1)",
        "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT 5.0)",
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.2) Gecko/20040804 Netscape/7.2 (ax)",
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_6; en-us) AppleWebKit/525.27.1 (KHTML, like Gecko) Version/3.2.1 Safari/525.27.1",
        "Opera/9.63 (Windows NT 5.1; U; en) Presto/2.1.1",
        "Opera/7.0 (Windows NT 5.1; U) [en]",
        "Opera/6.0 (Windows NT 5.1; U) [en]"
    };

	private FilterChangeUserAgentDialog filterChangeUserAgentDialog = null;
	private String userAgent = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.proofsecure.paros.extension.filter.FilterAdaptor#getId()
	 */
	public int getId() {
		return 120;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.proofsecure.paros.extension.filter.FilterAdaptor#getName()
	 */
	public String getName() {
		return "Change user agent to other browsers.";
	}

	/**
	 * This method initializes filterReplaceDialog
	 * 
	 * @return com.proofsecure.paros.extension.filter.FilterReplaceDialog
	 */
	private FilterChangeUserAgentDialog getFilterChangeUserAgentDialog() {
		if (filterChangeUserAgentDialog == null) {
			filterChangeUserAgentDialog = new FilterChangeUserAgentDialog(getView().getMainFrame(), true);
		}
		return filterChangeUserAgentDialog;
	}

	public boolean isPropertyExists() {
		return true;
	}

	public void editProperty() {
		FilterChangeUserAgentDialog dialog = getFilterChangeUserAgentDialog();
		dialog.setView(getView());
		int result = dialog.showDialog();
		if (result == JOptionPane.CANCEL_OPTION) {
			return;
		}

		userAgent = userAgentHeader[dialog.getUserAgentItem()];

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.proofsecure.paros.extension.filter.FilterAdaptor#onHttpRequestSend
	 * (com.proofsecure.paros.network.HttpMessage)
	 */
	public void onHttpRequestSend(HttpMessage msg) {

		if (userAgent.equals("") || msg.getRequestHeader().isEmpty()) {
			return;
		}

		msg.getRequestHeader().setHeader(HttpHeader.USER_AGENT, userAgent);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.proofsecure.paros.extension.filter.FilterAdaptor#onHttpResponseReceive
	 * (com.proofsecure.paros.network.HttpMessage)
	 */
	public void onHttpResponseReceive(HttpMessage msg) {

	}

}
