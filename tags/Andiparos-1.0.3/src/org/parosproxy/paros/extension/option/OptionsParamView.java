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
	private static final String SHOW_SPLASH = "view.showSplash";
	private static final String HTTP_RESPONSE_TO_SITEMAP = "view.sitemap.httpResponseToSitemap";
	private static final String HTTP_RESPONSE_TO_SITEMAP_NO_200OK = "view.sitemap.httpResponseToSitemapNo200Ok";
	private static final String HTTP_RESPONSE_HIDE_404_FROM_SITEMAP = "view.sitemap.httpResponseHide404NotFound";
	
	private int processImages = 0;
	private int showSplash = 0;
	private int httpResponseToSitemap = 0;
	private int httpResponseToSitemapNo200Ok = 0;
	private int httpResponseHide404NotFound = 0;


	public OptionsParamView() { }

	protected void parse() {
		// use temp variable to check. Exception will be flagged if any error.
		processImages = getConfig().getInt(PROCESS_IMAGES, 0);
		showSplash = getConfig().getInt(SHOW_SPLASH, 0);
		httpResponseToSitemap = getConfig().getInt(HTTP_RESPONSE_TO_SITEMAP, 0);
		httpResponseToSitemapNo200Ok = getConfig().getInt(HTTP_RESPONSE_TO_SITEMAP_NO_200OK, 0);
		httpResponseHide404NotFound = getConfig().getInt(HTTP_RESPONSE_HIDE_404_FROM_SITEMAP, 0);
	}

	/**
	 * @return Returns the skipImage.
	 */
	public int getProcessImages() {
		return processImages;
	}
	
	/**
	 * @return Returns show splash
	 */
	public int getShowSplash() {
		return showSplash;
	}
	
	/**
	 * @return Returns whether HTTP Response code should be added to the sitemap
	 */
	public int getHttpResponseToSitemap() {
		return httpResponseToSitemap;
	}
	
	/**
	 * @return Returns whether only HTTP Response code other than 200 should be
	 * added to the sitemap
	 */
	public int getHttpResponseToSitemapNo200Ok() {
		return httpResponseToSitemapNo200Ok;
	}
	
	/**
	 * @return Returns whether HTTP Response code 404 files should be displayed in
	 * the sitemap or not
	 */
	public int getHttpResponseHide404NotFound() {
		return httpResponseHide404NotFound;
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
	 * @param showSplash
	 *        0 = Do not show the splash screen
	 *    Other = Show the splash screen
	 */
	public void setShowSplash(int showSplash) {
		this.showSplash = showSplash;
		getConfig().setProperty(SHOW_SPLASH, Integer.toString(showSplash));
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
	
	
	/**
	 * @param httpResponseToSitemapNo200Ok
	 *        0 = Add all response codes to the sitemap
	 *    Other = Only add response codes other than 200 OK to the sitemap
	 */
	public void setHttpResponseToSitemapNo200Ok(int httpResponseToSitemapNo200Ok) {
		this.httpResponseToSitemapNo200Ok = httpResponseToSitemapNo200Ok;
		getConfig().setProperty(HTTP_RESPONSE_TO_SITEMAP_NO_200OK, httpResponseToSitemapNo200Ok);
	}
	
	/**
	 * @param httpResponseHide404NotFound
	 *        0 = Show 404 response codes in the sitemap
	 *    Other = Hide 404 response codes from the sitemap
	 */
	public void setHttpResponseHide404NotFound(int httpResponseHide404NotFound) {
		this.httpResponseHide404NotFound = httpResponseHide404NotFound;
		getConfig().setProperty(HTTP_RESPONSE_HIDE_404_FROM_SITEMAP, httpResponseHide404NotFound);
	}
	

	public boolean isProcessImages() {
		return !(processImages == 0);
	}
	
	public boolean isShowSplash() {
		return !(showSplash == 0);
	}
	
	public boolean isHttpResponseToSitemap() {
		return !(httpResponseToSitemap == 0);
	}
	
	public boolean isHttpResponseToSitemapNo200Ok() {
		return !(httpResponseToSitemapNo200Ok == 0);
	}
	
	public boolean isHttpResponseHide404NotFound() {
		return !(httpResponseHide404NotFound == 0);
	}
	
	

}
