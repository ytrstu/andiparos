/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2006 Chinotec Technologies Company
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
package org.parosproxy.paros.extension.history;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Cursor;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;

public class LogPanelCellRenderer extends JPanel implements ListCellRenderer {

	private static final long serialVersionUID = 5235089457655708192L;

	private JLabel imgFlag = null;
	private JLabel txtId = null;
	private JLabel txtMethod = null;
	private JLabel txtURI = null;
	private JLabel txtStatus = null;
	private JLabel txtReason = null;
	private JLabel txtRTT = null;
	private JLabel txtTimestamp = null;
	private JLabel txtTag = null;

	/**
	 * This is the default constructor
	 */
	public LogPanelCellRenderer() {
		super();

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.gridx = 8;
		gridBagConstraints4.gridy = 0;
		gridBagConstraints4.weightx = 0.0D;
		gridBagConstraints4.ipadx = 4;
		gridBagConstraints4.ipady = 1;
		gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
		
		GridBagConstraints gbc_timestamp = new GridBagConstraints();
		gbc_timestamp.anchor = GridBagConstraints.WEST;
		gbc_timestamp.gridx = 7;
		gbc_timestamp.gridy = 0;
		gbc_timestamp.weightx = 0.15D;
		gbc_timestamp.ipadx = 10;
		gbc_timestamp.ipady = 1;
		gbc_timestamp.fill = GridBagConstraints.HORIZONTAL;

		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		gridBagConstraints3.gridy = 0;
		gridBagConstraints3.weightx = 0.0D;
		gridBagConstraints3.ipadx = 4;
		gridBagConstraints3.ipady = 1;
		gridBagConstraints3.gridx = 6;

		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 5;
		gridBagConstraints21.ipadx = 4;
		gridBagConstraints21.ipady = 1;
		gridBagConstraints21.weightx = 0.0D;
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.gridy = 0;

		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.gridy = 0;
		gridBagConstraints11.weightx = 0.0D;
		gridBagConstraints11.ipadx = 4;
		gridBagConstraints11.ipady = 1;
		gridBagConstraints11.gridx = 4;

		txtTag = new JLabel();
		txtTag.setText("");
		txtTag.setBackground(SystemColor.text);
		txtTag.setHorizontalAlignment(SwingConstants.LEFT);
		txtTag.setPreferredSize(new Dimension(70, 16));
		txtTag.setMinimumSize(new Dimension(70, 16));
		txtTag.setFont(new Font("Default", Font.PLAIN, 12));
		txtTag.setOpaque(true);
		
		txtTimestamp = new JLabel();
		txtTimestamp.setText("");
		txtTimestamp.setBackground(SystemColor.text);
		txtTimestamp.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTimestamp.setPreferredSize(new Dimension(40, 16));
		txtTimestamp.setMinimumSize(new Dimension(40, 16));
		txtTimestamp.setFont(new Font("Default", Font.PLAIN, 12));
		txtTimestamp.setOpaque(true);
		
		txtRTT = new JLabel();
		txtRTT.setText("");
		txtRTT.setBackground(SystemColor.text);
		txtRTT.setHorizontalAlignment(SwingConstants.RIGHT);
		txtRTT.setPreferredSize(new Dimension(55, 16));
		txtRTT.setMinimumSize(new Dimension(55, 16));
		txtRTT.setFont(new Font("Default", Font.PLAIN, 12));
		txtRTT.setOpaque(true);
		
		txtReason = new JLabel();
		txtReason.setText("");
		txtReason.setBackground(SystemColor.text);
		txtReason.setHorizontalAlignment(SwingConstants.LEFT);
		txtReason.setPreferredSize(new Dimension(85, 16));
		txtReason.setMinimumSize(new Dimension(85, 16));
		txtReason.setFont(new Font("Default", Font.PLAIN, 12));
		txtReason.setOpaque(true);
		txtReason.setVisible(true);
		txtStatus = new JLabel();
		
		txtStatus.setText("");
		txtStatus.setBackground(SystemColor.text);
		txtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatus.setPreferredSize(new Dimension(30, 16));
		txtStatus.setMinimumSize(new Dimension(30, 16));
		txtStatus.setFont(new Font("Default", Font.PLAIN, 12));
		txtStatus.setOpaque(true);

		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.weightx = 0.75D;
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.ipadx = 4;
		gridBagConstraints2.ipady = 1;
		gridBagConstraints2.gridx = 3;

		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.anchor = GridBagConstraints.WEST;
		gridBagConstraints1.weightx = 0.0D;
		gridBagConstraints1.ipadx = 4;
		gridBagConstraints1.ipady = 1;
		gridBagConstraints1.fill = GridBagConstraints.NONE;
		gridBagConstraints1.gridx = 2;

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.weightx = 0.0D;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 1;
		gridBagConstraints.gridx = 1;

		GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
		gridBagConstraints0.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints0.gridy = 0;
		gridBagConstraints0.anchor = GridBagConstraints.WEST;
		gridBagConstraints0.weightx = 0.0D;
		gridBagConstraints0.ipadx = 4;
		gridBagConstraints0.ipady = 1;
		gridBagConstraints0.gridx = 0;

		txtURI = new JLabel();
		txtURI.setText("");
		txtURI.setBackground(SystemColor.text);
		txtURI.setHorizontalAlignment(SwingConstants.LEFT);
		txtURI.setPreferredSize(new Dimension(420, 16));
		txtURI.setMinimumSize(new Dimension(420, 16));
		txtURI.setFont(new Font("Default", Font.PLAIN, 12));
		txtURI.setOpaque(true);

		txtMethod = new JLabel();
		txtMethod.setText("");
		txtMethod.setBackground(SystemColor.text);
		txtMethod.setHorizontalAlignment(SwingConstants.LEFT);
		txtMethod.setPreferredSize(new Dimension(45, 16));
		txtMethod.setMinimumSize(new Dimension(45, 16));
		txtMethod.setFont(new Font("Default", Font.PLAIN, 12));
		txtMethod.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		txtMethod.setOpaque(true);

		txtId = new JLabel();
		txtId.setText("");
		txtId.setBackground(SystemColor.text);
		txtId.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setPreferredSize(new Dimension(40, 16));
		txtId.setMinimumSize(new Dimension(40, 16));
		txtId.setFont(new Font("Default", Font.PLAIN, 12));
		txtId.setOpaque(true);

		imgFlag = new JLabel();
		imgFlag.setName("imgFlag");
		imgFlag.setPreferredSize(new Dimension(16, 16));
		imgFlag.setMinimumSize(new Dimension(16, 16));
		imgFlag.setOpaque(true);
	

		this.setLayout(new GridBagLayout());
		this.setSize(328, 11);
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.add(imgFlag, gridBagConstraints0);
		this.add(txtId, gridBagConstraints);
		this.add(txtMethod, gridBagConstraints1);
		this.add(txtURI, gridBagConstraints2);
		this.add(txtStatus, gridBagConstraints11);
		this.add(txtReason, gridBagConstraints21);
		this.add(txtRTT, gridBagConstraints3);
		this.add(txtTimestamp, gbc_timestamp);
		this.add(txtTag, gridBagConstraints4);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		HistoryReference ref = (HistoryReference) value;
		txtId.setText(Integer.toString(ref.getHistoryId()));
		
		Color flaggedBgColor = new Color(200,100,120);
		Color flaggedFgColor = new Color(255,255,255);

		final ImageIcon standardTag = new ImageIcon(getClass().getResource("/resource/tags/tag_gray.png"));
		final ImageIcon selectedTag = new ImageIcon(getClass().getResource("/resource/tags/tag_blue.png"));
		final ImageIcon flaggedTag = new ImageIcon(getClass().getResource("/resource/tags/tag_red.png"));
		
		boolean isFlagged = false;
		

		HttpMessage msg = new HttpMessage();
		
		try {
			msg = ref.getHttpMessage();
			txtMethod.setText(msg.getRequestHeader().getMethod());
			txtURI.setText(msg.getRequestHeader().getURI().toString());
			txtStatus.setText(Integer.toString(msg.getResponseHeader().getStatusCode()));
			txtReason.setText(msg.getResponseHeader().getReasonPhrase());
			txtRTT.setText(msg.getTimeElapsedMillis() + "ms");
			
			String date = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new java.util.Date (msg.getTimeSentMillis()));
			txtTimestamp.setText(date);
			
			txtTag.setText(msg.getTag());
			isFlagged = msg.getFlag();
			if(isFlagged == true) {
				imgFlag.setIcon(flaggedTag);
			} else {
				imgFlag.setIcon(standardTag);
			}
			

		} catch (HttpMalformedHeaderException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (isSelected) {
			if(!isFlagged) {
				imgFlag.setIcon(selectedTag);
			}
			imgFlag.setBackground(list.getSelectionBackground());
			imgFlag.setForeground(list.getSelectionForeground());
			txtId.setBackground(list.getSelectionBackground());
			txtId.setForeground(list.getSelectionForeground());
			txtMethod.setBackground(list.getSelectionBackground());
			txtMethod.setForeground(list.getSelectionForeground());
			txtURI.setBackground(list.getSelectionBackground());
			txtURI.setForeground(list.getSelectionForeground());
			txtStatus.setBackground(list.getSelectionBackground());
			txtStatus.setForeground(list.getSelectionForeground());
			txtReason.setBackground(list.getSelectionBackground());
			txtReason.setForeground(list.getSelectionForeground());
			txtRTT.setBackground(list.getSelectionBackground());
			txtRTT.setForeground(list.getSelectionForeground());
			txtTimestamp.setBackground(list.getSelectionBackground());
			txtTimestamp.setForeground(list.getSelectionForeground());
			txtTag.setBackground(list.getSelectionBackground());
			txtTag.setForeground(list.getSelectionForeground());
			
		} else if (isFlagged) {
			imgFlag.setBackground(flaggedBgColor);
			imgFlag.setForeground(flaggedFgColor);
			txtId.setBackground(flaggedBgColor);
			txtId.setForeground(flaggedFgColor);
			txtMethod.setBackground(flaggedBgColor);
			txtMethod.setForeground(flaggedFgColor);
			txtURI.setBackground(flaggedBgColor);
			txtURI.setForeground(flaggedFgColor);
			txtStatus.setBackground(flaggedBgColor);
			txtStatus.setForeground(flaggedFgColor);
			txtReason.setBackground(flaggedBgColor);
			txtReason.setForeground(flaggedFgColor);
			txtRTT.setBackground(flaggedBgColor);
			txtRTT.setForeground(flaggedFgColor);
			txtTimestamp.setBackground(flaggedBgColor);
			txtTimestamp.setForeground(flaggedFgColor);
			txtTag.setBackground(flaggedBgColor);
			txtTag.setForeground(flaggedFgColor); 
		
		} else {
			Color darker = new Color(list.getBackground().getRGB() & 0xFFECECEC);
			imgFlag.setBackground(list.getBackground());
			imgFlag.setForeground(list.getForeground());
			txtId.setBackground(list.getBackground());
			txtId.setForeground(list.getForeground());
			txtMethod.setBackground(darker);
			txtMethod.setForeground(list.getForeground());
			txtURI.setBackground(list.getBackground());
			txtURI.setForeground(list.getForeground());
			txtStatus.setBackground(darker);
			txtStatus.setForeground(list.getForeground());
			txtReason.setBackground(list.getBackground());
			txtReason.setForeground(list.getForeground());
			txtRTT.setBackground(darker);
			txtRTT.setForeground(list.getForeground());
			txtTimestamp.setBackground(list.getBackground());
			txtTimestamp.setForeground(list.getForeground());
			txtTag.setBackground(darker);
			txtTag.setForeground(list.getForeground());
		}
		
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		return this;

	}
}
