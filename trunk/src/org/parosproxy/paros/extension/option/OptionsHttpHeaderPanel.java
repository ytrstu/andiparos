
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
import org.parosproxy.paros.network.ConnectionParam;
import org.parosproxy.paros.view.AbstractParamPanel;

public class OptionsHttpHeaderPanel extends AbstractParamPanel {

	private static final long serialVersionUID = 2693897440860124754L;
	private JPanel panelMisc = null;
	private JLabel labelUserAgent = null;
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
			
			panelMisc.setBorder(BorderFactory.createTitledBorder(null, "HTTP Header Customization",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.PLAIN, 11),
					Color.black));
			
			labelUserAgent = new JLabel();
			labelUserAgent.setText("Custom User-Agent");
			
			GridBagConstraints gbcLabel = new GridBagConstraints();
			GridBagConstraints gbcTextField = new GridBagConstraints();
			
			gbcLabel.gridx = 0;
			gbcLabel.gridy = 0;
			gbcLabel.ipadx = 0;
			gbcLabel.ipady = 0;
			gbcLabel.anchor = GridBagConstraints.WEST;
			gbcLabel.insets = new Insets(2,2,2,2);
			gbcLabel.weightx = 0.5D;
			gbcLabel.fill = GridBagConstraints.HORIZONTAL;
			
			gbcTextField.gridx = 0;
			gbcTextField.gridy = 1;
			gbcTextField.weightx = 0.5D;
			gbcTextField.fill = GridBagConstraints.HORIZONTAL;
			gbcTextField.ipadx = 100;
			gbcTextField.ipady = 0;
			gbcTextField.anchor = GridBagConstraints.EAST;
			gbcTextField.insets = new Insets(2,2,2,2);
			
			panelMisc.add(labelUserAgent, gbcLabel);
			panelMisc.add(getTxtCustomUserAgent(), gbcTextField);
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
		options.getHttpHeaderParam().setCustomUserAgent(getTxtCustomUserAgent().getText());
		
	}

}
