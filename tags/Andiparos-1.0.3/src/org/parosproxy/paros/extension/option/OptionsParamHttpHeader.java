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

package org.parosproxy.paros.extension.option;

import org.parosproxy.paros.common.AbstractParam;


public class OptionsParamHttpHeader extends AbstractParam {

	private static final String HTTP_HEADER_CUSTOM_USER_AGENT = "filter.httpHeader.customUserAgent";
	private String txtCustomUserAgent = null;

	public OptionsParamHttpHeader() { }

	protected void parse() {
		txtCustomUserAgent = getConfig().getString(HTTP_HEADER_CUSTOM_USER_AGENT, "");
		setCustomUserAgent(txtCustomUserAgent);
	}

	/**
	 * @return Returns the custom User-Agent string
	 */
	public String getCustomUserAgent() {
		return txtCustomUserAgent;
	}
	
	/**
	 * @param txtCustomUserAgent
	 */
	public void setCustomUserAgent(String txtCustomUserAgent) {
		this.txtCustomUserAgent = txtCustomUserAgent.trim();
		getConfig().setProperty(HTTP_HEADER_CUSTOM_USER_AGENT, txtCustomUserAgent);
	}
	
	public boolean isCustomUserAgent() {
		return !(txtCustomUserAgent.equals(""));
	}

}
