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

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.network.HttpSender;
import org.parosproxy.paros.view.AbstractParamPanel;

public class OptionsHttpHeaderPanel extends AbstractParamPanel {

	private static final long serialVersionUID = 2693897440860124754L;
	private JPanel panelMisc = null;
	private JTextField txtCustomUserAgent = null;

	public OptionsHttpHeaderPanel() {
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
		this.setName("HTTP Header");
		this.add(getPanelMisc(), getPanelMisc().getName()); 
	}

	/**
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelMisc() {
		if (panelMisc == null) {
			
			panelMisc = new JPanel();
			panelMisc.setName("HTTP Header Panel");
			panelMisc.setLayout(new GridBagLayout());
			panelMisc.setBorder(BorderFactory.createTitledBorder(null,
					"Custom User-Agent",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.PLAIN, 11),
					Color.black));
			
			JLabel labelUserAgent = new JLabel();
			labelUserAgent.setText("User-Agent");
			
			GridBagConstraints gbcUserAgentLabel = new GridBagConstraints();
			GridBagConstraints gbcUserAgent = new GridBagConstraints();
			
			gbcUserAgentLabel.gridx = 0;
			gbcUserAgentLabel.gridy = 0;
			gbcUserAgentLabel.ipadx = 0;
			gbcUserAgentLabel.ipady = 0;
			gbcUserAgentLabel.weightx = 0.5D;
			gbcUserAgentLabel.insets = new Insets(2,2,2,2);
			gbcUserAgentLabel.anchor = GridBagConstraints.WEST;
			gbcUserAgentLabel.fill = GridBagConstraints.HORIZONTAL;
			
			gbcUserAgent.gridx = 1;
			gbcUserAgent.gridy = 0;
			gbcUserAgent.ipadx = 100;
			gbcUserAgent.ipady = 0;
			gbcUserAgent.weightx = 0.5D;
			gbcUserAgent.insets = new Insets(2,2,2,2);
			gbcUserAgent.anchor = GridBagConstraints.EAST;
			gbcUserAgent.fill = GridBagConstraints.HORIZONTAL;
			
			
			panelMisc.add(labelUserAgent, gbcUserAgentLabel);
			panelMisc.add(getTxtCustomUserAgent(), gbcUserAgent);
		}
		return panelMisc;
	}


	private JTextField getTxtCustomUserAgent() {
		if (txtCustomUserAgent == null) {
			txtCustomUserAgent = new JTextField();
		}
		return txtCustomUserAgent;
	}
	
	
	public void initParam(Object obj) {
		OptionsParam options = (OptionsParam) obj;
		getTxtCustomUserAgent().setText(options.getHttpHeaderParam().getCustomUserAgent());
	}

	public void validateParam(Object obj) {
		// no validation needed
	}

	public void saveParam(Object obj) throws Exception {
		OptionsParam options = (OptionsParam) obj;
		String userAgent = getTxtCustomUserAgent().getText();
		options.getHttpHeaderParam().setCustomUserAgent(userAgent);
		
		// Reset the user-agent
		HttpSender.setUserAgent(userAgent);
	}

}
