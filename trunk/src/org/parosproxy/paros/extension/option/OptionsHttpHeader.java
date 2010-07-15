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


public class OptionsHttpHeader extends AbstractParam {

	private static final String CUSTOM_HTTP_HEADER_USER_AGENT = "filter.httpHeader.customUserAgent";
	private String customUserAgent = null;
	

	public OptionsHttpHeader() { }

	protected void parse() {
		// use temp variable to check. Exception will be flagged if any error.
		customUserAgent = getConfig().getString(CUSTOM_HTTP_HEADER_USER_AGENT, "");
	}

	/**
	 * @return Returns the User-Agent
	 */
	public String getCustomUserAgent() {
		return customUserAgent;
	}


	/**
	 * @param customUserAgent
	 */
	public void setCustomUserAgent(String customUserAgent) {
		this.customUserAgent = customUserAgent;
		getConfig().setProperty(CUSTOM_HTTP_HEADER_USER_AGENT, customUserAgent);
	}
	

	public boolean isCustomUserAgent() {
		return !(customUserAgent != "");
	}

}
