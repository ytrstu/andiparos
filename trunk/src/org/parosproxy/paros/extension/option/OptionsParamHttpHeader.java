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

package org.parosproxy.paros.extension.option;

import org.parosproxy.paros.common.AbstractParam;


public class OptionsParamHttpHeader extends AbstractParam {

	private static final String HTTP_HEADER_CUSTOM_USER_AGENT = "filter.httpHeaders.customUserAgent";
	private String txtCustomUserAgent = null;

	public OptionsParamHttpHeader() { }

	protected void parse() {
		txtCustomUserAgent = getConfig().getString(HTTP_HEADER_CUSTOM_USER_AGENT, "");
		setCustomUserAgent(txtCustomUserAgent);
	}

	/**
	 * @return Returns the skipImage.
	 */
	public String getCustomUserAgent() {
		return txtCustomUserAgent;
	}
	
	/**
	 * @param txtCustomUserAgent
	 */
	public void setCustomUserAgent(String txtCustomUserAgent) {
		this.txtCustomUserAgent = txtCustomUserAgent.trim();
		getConfig().setProperty(HTTP_HEADER_CUSTOM_USER_AGENT, this.txtCustomUserAgent);
	}
	
	public boolean isCustomUserAgent() {
		return !(txtCustomUserAgent.equals(""));
	}

}
