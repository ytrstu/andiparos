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
package org.parosproxy.paros.extension.spider;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.log4j.Logger;
import org.parosproxy.paros.extension.AbstractDialog;
import org.parosproxy.paros.model.SiteNode;
import org.parosproxy.paros.network.HttpMessage;
/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SpiderDialog extends AbstractDialog implements TreeSelectionListener {

	private static final long serialVersionUID = 5452654312704779847L;

	private JPanel jPanel = null;
	private JLabel txtDisplay = null;
	private JButton btnStart = null;
	private JButton btnStop = null;
	
	private ExtensionSpider extension = null;
	
	private JProgressBar progressBar = null;
	private JLabel txtNumCrawled = null;
	private JLabel txtOutstandingCrawl = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JScrollPane jScrollPane = null;
	
	// ZAP: Added logger
	private Logger logger = Logger.getLogger(SpiderDialog.class);
	
    /**
     * @throws HeadlessException
     */
    public SpiderDialog() throws HeadlessException  {
        super();
 		initialize();
    }

    /**
     * @param arg0
     * @param arg1
     * @throws HeadlessException
     */
    public SpiderDialog(Frame arg0, boolean arg1) throws HeadlessException {
        super(arg0, arg1);
        initialize();
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setTitle("Spider");
        this.setContentPane(getJPanel());
        this.setSize(407, 255);
        this.addWindowListener(new WindowAdapter() {   
        	public void windowOpened(WindowEvent e) {    
        	    extension.getMenuItemSpider().setEnabled(false);
                extension.getPopupMenuSpider().setEnabled(false);
        	} 

        	public void windowClosing(WindowEvent e) {    
        	    btnStop.doClick();
                extension.getView().getSiteTreePanel().getTreeSite().removeTreeSelectionListener(SpiderDialog.this);
        	    extension.getMenuItemSpider().setEnabled(true);
                extension.getPopupMenuSpider().setEnabled(true);
                extension.clear();
        	}
        });

		pack();
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			JLabel jLabel2 = new JLabel();
			JLabel jLabel1 = new JLabel();
			JLabel jLabel = new JLabel();
			

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 5;
			gridBagConstraints2.insets = new Insets(2,2,2,2);
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 5;
			gridBagConstraints3.insets = new Insets(2,2,2,10);
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			jLabel.setText("<html><body><p>The site/folder/URL chosen will be crawled. "
					+ "You may press stop and resume the crawl afterwards. The spider will crawl "
					+ "hyperlinks and attempt to submit forms.</p></body></html>");
			jLabel.setPreferredSize(new Dimension(400,48));
			jLabel.setMinimumSize(new Dimension(400,48));
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 4;
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.insets = new Insets(2,10,2,10);
			gridBagConstraints4.gridwidth = 3;
			gridBagConstraints4.ipady = 10;
			gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 3;
			gridBagConstraints11.insets = new Insets(2,10,8,10);
			gridBagConstraints11.gridwidth = 3;
			jLabel1.setText("URL crawling:");
			gridBagConstraints12.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints12.fill = GridBagConstraints.NONE;
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.insets = new Insets(2,10,2,5);
			gridBagConstraints12.weightx = 2.0D;
			gridBagConstraints12.gridwidth = 3;
			gridBagConstraints12.ipadx = 0;
			gridBagConstraints12.ipady = 0;
			jLabel2.setText(" ");
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.gridy = 5;
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.weightx = 1.0D;
			gridBagConstraints13.insets = new Insets(2,10,2,5);
			gridBagConstraints5.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.insets = new Insets(5,10,5,10);
			gridBagConstraints5.gridwidth = 3;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 6;
			gridBagConstraints14.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints14.fill = GridBagConstraints.NONE;
			gridBagConstraints14.weightx = 1.0D;
			gridBagConstraints14.weighty = 0.2D;
			gridBagConstraints14.gridwidth = 3;
			gridBagConstraints14.insets = new Insets(2,10,2,10);
			jLabel5.setText("");
			jLabel5.setPreferredSize(new Dimension(1,16));
			jLabel5.setMinimumSize(new Dimension(1,16));
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.weighty = 0.0D;
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.insets = new Insets(2,10,5,10);
			gridBagConstraints15.gridwidth = 3;
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints15.ipadx = 0;
			gridBagConstraints15.ipady = 10;
			jPanel.add(getJPanel1(), gridBagConstraints5);
			jPanel.add(jLabel1, gridBagConstraints12);
			jPanel.add(getJScrollPane(), gridBagConstraints15);
			jPanel.add(getProgressBar(), gridBagConstraints11);
			jPanel.add(jLabel, gridBagConstraints4);
			jPanel.add(jLabel2, gridBagConstraints13);
			jPanel.add(getBtnStart(), gridBagConstraints2);
			jPanel.add(getBtnStop(), gridBagConstraints3);
			jPanel.add(jLabel5, gridBagConstraints14);
		}
		return jPanel;
	}
	/**
	 * This method initializes txtDisplay	
	 * 	
	 * @return JTextField	
	 */    
	JLabel getTxtDisplay() {
		if (txtDisplay == null) {
			txtDisplay = new JLabel("");
			txtDisplay.setHorizontalAlignment(JTextField.LEFT);
			txtDisplay.setAlignmentX(0.0F);
			txtDisplay.setPreferredSize(new Dimension(350,30));
			txtDisplay.setText("Not started.\r\n");
			txtDisplay.setMinimumSize(new Dimension(350,30));
			txtDisplay.setMaximumSize(new Dimension(350,30));
			txtDisplay.setVerticalAlignment(SwingConstants.TOP);
		}
		return txtDisplay;
	}
	/**
	 * This method initializes btnStart	
	 * 	
	 * @return JButton	
	 */    
	private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton();
			btnStart.setText("Start");
			btnStart.setMinimumSize(new Dimension(75,30));
			btnStart.setPreferredSize(new Dimension(75,30));
			btnStart.setMaximumSize(new Dimension(100,40));
			btnStart.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
	                extension.getView().getSiteTreePanel().getTreeSite().removeTreeSelectionListener(SpiderDialog.this);
				    btnStart.setEnabled(false);
				    btnStop.setEnabled(true);
				    extension.startSpider();
				}
			});
		}
		return btnStart;
	}
	
	/**
	 * This method initializes btnStop	
	 * 	
	 * @return JButton	
	 */    
	private JButton getBtnStop() {
		if (btnStop == null) {
			btnStop = new JButton();
			btnStop.setText("Stop");
			btnStop.setMaximumSize(new Dimension(100,40));
			btnStop.setMinimumSize(new Dimension(70,30));
			btnStop.setPreferredSize(new Dimension(70,30));
			btnStop.setEnabled(false);
			btnStop.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {    
                    btnStop.setEnabled(false);
			        Thread t = new Thread(new Runnable() {
			            public void run() {
                            if (extension.getSpider() != null) {
                                extension.getSpider().stop();
                            }
			    			try {
                                EventQueue.invokeAndWait(new Runnable() {
                                	public void run() {
                                        btnStart.setText("Resume");
                                        btnStart.setEnabled(true);
                                	}
                                });
                            } catch (Exception e) {
                            }
			            }
			        });
			        t.start();
				}
			});

		}
		return btnStop;
	}
	
	void setPlugin(ExtensionSpider plugin) {
	    this.extension = plugin;
        plugin.getView().getSiteTreePanel().getTreeSite().addTreeSelectionListener(this);

	}
	
	/**
	 * This method initializes progressBar	
	 * 	
	 * @return JProgressBar	
	 */    
	JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
		}
		return progressBar;
	}
	
	public void valueChanged(TreeSelectionEvent e) {
	    JTree siteTree = extension.getView().getSiteTreePanel().getTreeSite();
	    SiteNode node = (SiteNode) siteTree.getLastSelectedPathComponent();
	    extension.setStartNode(node);
        if (node.isRoot()) {
            getTxtDisplay().setText("All sites will be crawled");
        } else {
            try {
                HttpMessage msg = node.getHistoryReference().getHttpMessage();
                if (msg != null) {
                    String tmp = msg.getRequestHeader().getURI().toString();
                    getTxtDisplay().setText(tmp);
                }
            } catch (Exception e1) {
            	// ZAP: Log the exception
            	logger.error(e1.getMessage(), e1);
            }
		}
	}
	
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return JTextField	
	 */    
	JLabel getTxtNumCrawled() {
		if (txtNumCrawled == null) {
			txtNumCrawled = new JLabel("      ");
		}
		return txtNumCrawled;
	}
	
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return JTextField	
	 */    
	JLabel getTxtOutstandingCrawl() {
		if (txtOutstandingCrawl == null) {
			txtOutstandingCrawl = new JLabel("      ");
		}
		return txtOutstandingCrawl;
	}
	
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jLabel3.setText("No. crawled:");
			jLabel4.setText("No. to crawl:");
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.insets = new Insets(5,0,5,0);
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.fill = GridBagConstraints.NONE;
			gridBagConstraints7.insets = new Insets(5,10,5,10);
			gridBagConstraints7.anchor = GridBagConstraints.CENTER;
			gridBagConstraints7.ipadx = 50;
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.insets = new Insets(5,0,5,0);
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridx = 3;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.insets = new Insets(5,10,5,10);
			gridBagConstraints9.ipadx = 50;
			jPanel1.add(jLabel3, gridBagConstraints6);
			jPanel1.add(getTxtNumCrawled(), gridBagConstraints7);
			jPanel1.add(jLabel4, gridBagConstraints8);
			jPanel1.add(getTxtOutstandingCrawl(), gridBagConstraints9);
		}
		return jPanel1;
	}
	
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			jScrollPane.setPreferredSize(new Dimension(0,35));
			jScrollPane.setViewportView(getTxtDisplay());
			jScrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		}
		return jScrollPane;
	}
}
