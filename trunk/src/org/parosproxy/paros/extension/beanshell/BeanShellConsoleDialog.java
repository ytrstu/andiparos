/*
 * Copyright (C) 2010, Andiparos Project
 * 
 * Code contributed by Stephen de Vries
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

package org.parosproxy.paros.extension.beanshell;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.parosproxy.paros.extension.AbstractDialog;
import org.parosproxy.paros.extension.Extension;
import org.parosproxy.paros.extension.ViewDelegate;
import org.parosproxy.paros.model.Model;
import org.parosproxy.paros.network.HttpSender;
import org.parosproxy.paros.view.BeanShellPanel;

import bsh.EvalError;
import bsh.Interpreter;

/**
*
* To change the template for this generated type comment go to
* Window - Preferences - Java - Code Generation - Code and Comments
*/
public class BeanShellConsoleDialog extends AbstractDialog {

	private BeanShellPanel beanShellPanel = null;
	private JPanel panelCommand = null;
	private JButton btnEvaluate = null;
	private JButton btnLoad = null;
	private JButton btnSave = null;
	private JButton btnSaveAs = null;
	private Extension extension = null;
	private Interpreter interpreter = null;
	private String scriptsDir = System.getProperty("user.dir")+"/scripts/";
	private File currentScriptFile = null;
	private ViewDelegate view = null;

	private JPanel jPanel = null;
	
   /**
    * @throws HeadlessException
    */
   public BeanShellConsoleDialog() throws HeadlessException {
       super();
       initialize();
       
   }

   /**
    * @param arg0
    * @param arg1
    * @throws HeadlessException
    */
   public BeanShellConsoleDialog(Frame parent, boolean modal, Extension extension) throws HeadlessException {
       super(parent, modal);
       this.extension = extension;
       initialize();

   }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
	    getBeanShellPanel().getPanelOption().add(getPanelCommand(), "");

	    this.addWindowListener(new java.awt.event.WindowAdapter() { 
	    	public void windowClosing(java.awt.event.WindowEvent e) {
	    	    //Don't think we need todo anything here
	    	}
	    });

	    this.setContentPane(getJPanel());
	    
	}
	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPanelCommand() {
		if (panelCommand == null) {
			panelCommand = new JPanel();
			panelCommand.setLayout(new FlowLayout());			
			panelCommand.add(getBtnLoad());
			panelCommand.add(getBtnSave());
			panelCommand.add(getBtnSaveAs());
			panelCommand.add(getBtnEvaluate());
		}
		return panelCommand;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnEvaluate() {
		if (btnEvaluate == null) {
			btnEvaluate = new JButton();
			btnEvaluate.setText("Evaluate");
			
			btnEvaluate.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					beanShellEval(getBeanShellPanel().getTxtEditor().getText());
				}
			});
		}
		return btnEvaluate;
	}
	
	private void beanShellEval(String cmd) {
		try {	
			getInterpreter().eval(cmd);
		} catch (EvalError ex) {
			getInterpreter().error(ex.getMessage());
		}
	}
	
	private String loadScript(File file) throws IOException {
		BufferedReader input = null;
		
		input = new BufferedReader( new FileReader(file) );
		String str;
		StringBuffer temp = new StringBuffer();
		while ((str = input.readLine()) != null) {
			temp.append(str);
			temp.append(System.getProperty("line.separator"));
		}							
		input.close();
		return (temp.toString());
	}
	
	private void saveScript(String contents, File file) throws IOException {
		BufferedWriter output = null;
		
		output = new BufferedWriter( new FileWriter(file) );
		output.write( contents );
		output.close();
	
	}
	
	private JButton getBtnLoad() {
		if (btnLoad == null) {
			btnLoad = new JButton();
			btnLoad.setText("Load...");
			
			btnLoad.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getBeanShellPanel().isSaved() == false) {
						int confirm = view.showConfirmDialog("Script is not saved, discard?");
						if (confirm == JOptionPane.CANCEL_OPTION) return;
					}
					JFileChooser fc = new JFileChooser(scriptsDir);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int result = fc.showOpenDialog(getBeanShellPanel());
					
					if(result == JFileChooser.APPROVE_OPTION) {
						try {
							String temp = loadScript(fc.getSelectedFile());
							getBeanShellPanel().getTxtEditor().setText(temp);
							getBeanShellPanel().setSaved(true);
							currentScriptFile = fc.getSelectedFile();
						} catch (IOException ee) {
							ee.printStackTrace();
						}
						
					}
				}
			});
		}
		return btnLoad;
	}
	
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("Save...");
			
			btnSave.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (currentScriptFile != null) {
						try {
							saveScript(getBeanShellPanel().getTxtEditor().getText(), currentScriptFile);
							getBeanShellPanel().setSaved(true);
						} catch (IOException ee) {
							ee.printStackTrace();
						}
						
					} else {
						JFileChooser fc = new JFileChooser(scriptsDir);
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						int result = fc.showSaveDialog(getBeanShellPanel());
						
						
						if (result == JFileChooser.APPROVE_OPTION) {
							try {
								saveScript(getBeanShellPanel().getTxtEditor().getText(), fc.getSelectedFile());
								getBeanShellPanel().setSaved(true);
								currentScriptFile = fc.getSelectedFile();
							} catch (IOException ee) {
								ee.printStackTrace();
							}
						}
					}
				}
			});
		}
		return btnSave;
	}	
	
	private JButton getBtnSaveAs() {
		if (btnSaveAs == null) {
			btnSaveAs = new JButton();
			btnSaveAs.setText("Save as...");
			
			btnSaveAs.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					JFileChooser fc = new JFileChooser(scriptsDir);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int result = fc.showSaveDialog(getBeanShellPanel());										
					if (result == JFileChooser.APPROVE_OPTION) {
						try {
							saveScript(getBeanShellPanel().getTxtEditor().getText(), fc.getSelectedFile());
							getBeanShellPanel().setSaved(true);
							currentScriptFile = fc.getSelectedFile();
						} catch (IOException ee) {
							ee.printStackTrace();
						}
					}
					
				}
			});
		}
		return btnSaveAs;
	}	
	

   public void setExtension(Extension extension) {
       this.extension = extension;
   }
   
   private Extension getExtension() {
       return extension;
   }
   
   public void setVisible(boolean show) {    
       super.setVisible(show);       
   }
	
	private BeanShellPanel getBeanShellPanel() {
	   if (beanShellPanel == null) {
		   beanShellPanel = new BeanShellPanel();
	   }
	   return beanShellPanel;
	}
	
	public Interpreter getInterpreter() {
		if (interpreter == null) {
			interpreter = new Interpreter(getBeanShellPanel().getConsole());
		}
		return interpreter;
	}
	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    	
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.NORTHWEST;
			
			HttpSender sender = new HttpSender(Model.getSingleton().getOptionsParam().getConnectionParam(), true);
			try {
				getInterpreter().set("model", Model.getSingleton());
				getInterpreter().set("sites", Model.getSingleton().getSession().getSiteTree());
				getInterpreter().set("sender", sender);
				getInterpreter().eval("setAccessibility(true)"); //This allows BeanShell users to access private members
				getInterpreter().eval("import org.apache.commons.httpclient.URI");
				getInterpreter().eval("import org.parosproxy.paros.network.*");
				getInterpreter().eval("import org.parosproxy.paros.model.*");
				getInterpreter().eval("import org.parosproxy.paros.db.*");
				getInterpreter().eval("import org.parosproxy.paros.model.*;");
			} catch (EvalError e) {
			    e.printStackTrace();
			}
			new Thread( getInterpreter() ).start();
			jPanel.add(getBeanShellPanel(), gridBagConstraints31);
		}
		return jPanel;
	}
	
    
	public void setView(ViewDelegate view) {
		this.view = view;
	}
}
