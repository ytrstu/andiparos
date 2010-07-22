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

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.view.AbstractParamPanel;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class OptionsViewPanel extends AbstractParamPanel {

	private static final long serialVersionUID = 6630367747797602028L;

	private JPanel panelViewOptionsGeneral = null;
	private JPanel panelViewOptionsSitemap = null;
	private JPanel panelView = null;
	
	private JCheckBox chkProcessImages = null;
	private JCheckBox chkShowSplash = null;
	private JCheckBox chkHttpResponseCodeToSitemap = null;
	private JCheckBox chkHttpResponseCodeToSitemapNo200Ok = null;
	private JCheckBox chkHttpResponseHide404NotFound = null;

	public OptionsViewPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new CardLayout());
		this.setName("View");
		this.add(getPanelView(), getPanelView().getName());
	}

	
	private JPanel getPanelView() {
		if (panelView == null) {
			panelView = new JPanel();
			panelView.setLayout(new GridBagLayout());
	        panelView.setName("View");
	        
			GridBagConstraints gbcViewOptionsGeneral = new GridBagConstraints();
	        GridBagConstraints gbcViewOptionsSitemap = new GridBagConstraints();
	        GridBagConstraints gbcSpace = new GridBagConstraints();
	        
	        gbcViewOptionsGeneral.gridx = 0;
	        gbcViewOptionsGeneral.gridy = 0;
	        gbcViewOptionsGeneral.weightx = 1.0D;
	        gbcViewOptionsGeneral.insets = new Insets(2,2,2,2);
	        gbcViewOptionsGeneral.anchor = GridBagConstraints.NORTHWEST;
	        gbcViewOptionsGeneral.fill = GridBagConstraints.HORIZONTAL;
	        
	        gbcViewOptionsSitemap.gridx = 0;
	        gbcViewOptionsSitemap.gridy = 1;
	        gbcViewOptionsSitemap.weightx = 1.0D;
	        gbcViewOptionsSitemap.insets = new Insets(2,2,2,2);
	        gbcViewOptionsSitemap.anchor = GridBagConstraints.NORTHWEST;
	        gbcViewOptionsSitemap.fill = GridBagConstraints.HORIZONTAL;
	        
	        gbcSpace.gridx = 0;
	        gbcSpace.gridy = 2;
	        gbcSpace.weightx = 1.0D;
	        gbcSpace.weighty = 1.0D;
	        gbcSpace.insets = new Insets(2,2,2,2);
	        gbcSpace.anchor = GridBagConstraints.NORTHWEST;
	        gbcSpace.fill = GridBagConstraints.BOTH;
	        
	        JLabel spaceLabel = new JLabel();
	        spaceLabel.setText(" ");
	        
			panelView.add(getPanelViewOptionsGeneral(), gbcViewOptionsGeneral);
			panelView.add(getPanelViewOptionsSitemap(), gbcViewOptionsSitemap);
			panelView.add(spaceLabel, gbcSpace);
		}
		return panelView;
	}
	
	
	/**
	 * This method initializes panelMisc
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelViewOptionsGeneral() {
		if (panelViewOptionsGeneral == null) {
			panelViewOptionsGeneral = new JPanel();
			panelViewOptionsGeneral.setLayout(new BoxLayout(panelViewOptionsGeneral,BoxLayout.Y_AXIS));
			panelViewOptionsGeneral.setSize(114, 132);
			panelViewOptionsGeneral.setBorder(BorderFactory.createTitledBorder(null,
				"General",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				new Font("Dialog", Font.PLAIN, 11),
				Color.black));
			
			Box box = Box.createVerticalBox();
			box.add(getChkProcessImages());
			box.add(getChkShowSplash());

			panelViewOptionsGeneral.add(box);
		}
		return panelViewOptionsGeneral;
	}
	
	private JPanel getPanelViewOptionsSitemap() {
		if (panelViewOptionsSitemap == null) {
			panelViewOptionsSitemap = new JPanel();
			panelViewOptionsSitemap.setLayout(new BoxLayout(panelViewOptionsSitemap,BoxLayout.Y_AXIS));
			panelViewOptionsSitemap.setSize(114, 132);
			panelViewOptionsSitemap.setName("Miscellenous");
			panelViewOptionsSitemap.setBorder(BorderFactory.createTitledBorder(null,
				"Sitemap Options",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				new Font("Dialog", Font.PLAIN, 11),
				Color.black));
			
			Box box = Box.createVerticalBox();
			box.add(getChkHttpResponseToSitemap());
			box.add(getChkHttpResponseToSitemapNo200Ok());
			box.add(getChkHttpResponseHide404NotFound());

			panelViewOptionsSitemap.add(box);
		}
		return panelViewOptionsSitemap;
	}

	// Button for enabling/disabling processing of images
	private JCheckBox getChkProcessImages() {
		if (chkProcessImages == null) {
			chkProcessImages = new JCheckBox();
			chkProcessImages.setText("Process images in HTTP requests/responses");
			chkProcessImages.setVerticalAlignment(SwingConstants.TOP);
			chkProcessImages.setVerticalTextPosition(SwingConstants.TOP);
		}
		return chkProcessImages;
	}
	
	// Button for enabling/disabling splash screen
	private JCheckBox getChkShowSplash() {
		if (chkShowSplash == null) {
			chkShowSplash = new JCheckBox();
			chkShowSplash.setText("Show splash screen");
			chkShowSplash.setVerticalAlignment(SwingConstants.TOP);
			chkShowSplash.setVerticalTextPosition(SwingConstants.TOP);
		}
		return chkShowSplash;
	}
	
	// Button for enabling/disabling HTTP response codes in the sitemap
	private JCheckBox getChkHttpResponseToSitemap() {
		if (chkHttpResponseCodeToSitemap == null) {
			chkHttpResponseCodeToSitemap = new JCheckBox();
			chkHttpResponseCodeToSitemap.setText("Add HTTP response codes to sitemap nodes (just affects newly added nodes)");
			chkHttpResponseCodeToSitemap.setVerticalAlignment(SwingConstants.TOP);
			chkHttpResponseCodeToSitemap.setVerticalTextPosition(SwingConstants.TOP);
		}
		return chkHttpResponseCodeToSitemap;
	}
	
	// Button for enabling/disabling HTTP response codes other than 200OK in the sitemap 
	private JCheckBox getChkHttpResponseToSitemapNo200Ok() {
		if (chkHttpResponseCodeToSitemapNo200Ok == null) {
			chkHttpResponseCodeToSitemapNo200Ok = new JCheckBox();
			chkHttpResponseCodeToSitemapNo200Ok.setText("Only add HTTP response codes other than 200 OK to sitemap nodes");
			chkHttpResponseCodeToSitemapNo200Ok.setVerticalAlignment(SwingConstants.TOP);
			chkHttpResponseCodeToSitemapNo200Ok.setVerticalTextPosition(SwingConstants.TOP);
		}
		return chkHttpResponseCodeToSitemapNo200Ok;
	}
	
	// Button for enabling/disabling hiding HTTP 404 response codes from the sitemap 
	private JCheckBox getChkHttpResponseHide404NotFound() {
		if (chkHttpResponseHide404NotFound == null) {
			chkHttpResponseHide404NotFound = new JCheckBox();
			chkHttpResponseHide404NotFound.setText("Hide sitemap nodes with a 404 - Not found HTTP response code");
			chkHttpResponseHide404NotFound.setVerticalAlignment(SwingConstants.TOP);
			chkHttpResponseHide404NotFound.setVerticalTextPosition(SwingConstants.TOP);
		}
		return chkHttpResponseHide404NotFound;
	}
	
	
	public void initParam(Object obj) {
		OptionsParam options = (OptionsParam) obj;
		getChkProcessImages().setSelected(options.getViewParam().getProcessImages() > 0);
		getChkShowSplash().setSelected(options.getViewParam().getShowSplash() > 0);
		getChkHttpResponseToSitemap().setSelected(options.getViewParam().getHttpResponseToSitemap() > 0);
		getChkHttpResponseToSitemapNo200Ok().setSelected(options.getViewParam().getHttpResponseToSitemapNo200Ok() > 0);
		getChkHttpResponseHide404NotFound().setSelected(options.getViewParam().getHttpResponseHide404NotFound() > 0);
	}

	public void validateParam(Object obj) {
		// no validation needed
	}

	public void saveParam(Object obj) throws Exception {
		OptionsParam options = (OptionsParam) obj;
		options.getViewParam().setProcessImages((getChkProcessImages().isSelected()) ? 1 : 0);
		options.getViewParam().setShowSplash((getChkShowSplash().isSelected()) ? 1 : 0);
		options.getViewParam().setHttpResponseToSitemap((getChkHttpResponseToSitemap().isSelected()) ? 1 : 0);
		options.getViewParam().setHttpResponseToSitemapNo200Ok((getChkHttpResponseToSitemapNo200Ok().isSelected()) ? 1 : 0);
		options.getViewParam().setHttpResponseHide404NotFound((getChkHttpResponseHide404NotFound().isSelected()) ? 1 : 0);
	}

}
