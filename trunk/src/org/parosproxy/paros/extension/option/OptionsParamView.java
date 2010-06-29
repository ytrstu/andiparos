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


public class OptionsParamView extends AbstractParam {

	private static final String PROCESS_IMAGES = "view.processImages";
	private static final String HTTP_RESPONSE_TO_SITEMAP = "view.httpResponseToSitemap";
	private int processImages = 0;
	private int httpResponseToSitemap = 0;


	public OptionsParamView() { }

	protected void parse() {
		// use temp variable to check. Exception will be flagged if any error.
		processImages = getConfig().getInt(PROCESS_IMAGES, 0);
		httpResponseToSitemap = getConfig().getInt(HTTP_RESPONSE_TO_SITEMAP, 0);
	}

	/**
	 * @return Returns the skipImage.
	 */
	public int getProcessImages() {
		return processImages;
	}
	
	/**
	 * @return Returns whether HTTP Response should be added to the sitemap
	 */
	public int getHttpResponseToSitemap() {
		return httpResponseToSitemap;
	}


	/**
	 * @param processImages
	 *        0 = Do not to process images
	 *    Other = Process images
	 */
	public void setProcessImages(int processImages) {
		this.processImages = processImages;
		getConfig().setProperty(PROCESS_IMAGES, Integer.toString(processImages));
	}
	
	/**
	 * @param httpResponseToSitemap
	 *        0 = Do not add reponse code to the sitemap
	 *    Other = Add the response code to the sitemap
	 */
	public void setHttpResponseToSitemap(int httpResponseToSitemap) {
		this.httpResponseToSitemap = httpResponseToSitemap;
		getConfig().setProperty(HTTP_RESPONSE_TO_SITEMAP, httpResponseToSitemap);
	}

	public boolean isProcessImages() {
		return !(processImages == 0);
	}
	
	public boolean isHttpResponseToSitemap() {
		return !(httpResponseToSitemap == 0);
	}

}
