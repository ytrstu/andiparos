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

import javax.swing.JMenuItem;

import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.parosproxy.paros.extension.ExtensionHookView;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ExtensionBeanShell extends ExtensionAdaptor {

	private BeanShellConsoleDialog beanShellConsoleDialog = null;
	private JMenuItem menuBeanShell = null;
	
    /**
     * 
     */
    public ExtensionBeanShell() {
        super();
 		initialize();
    }

    /**
     * @param name
     */
    public ExtensionBeanShell(String name) {
        super(name);
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setName("ExtensionBeanShell");
			
	}
	
	public void hook(ExtensionHook extensionHook) {
	    super.hook(extensionHook);
	    if (getView() != null) {
	        ExtensionHookView pv = extensionHook.getHookView();	        
	        extensionHook.getHookMenu().addToolsMenuItem(getMenuBeanShell());
	    }
	}
	   
	private JMenuItem getMenuBeanShell() {
		if (menuBeanShell == null) {
		    menuBeanShell = new JMenuItem();
		    menuBeanShell.setText("BeanShell Console...");
		    menuBeanShell.addActionListener(new java.awt.event.ActionListener() { 
		    	public void actionPerformed(java.awt.event.ActionEvent e) {
		    	    BeanShellConsoleDialog dialog = getBeanShellConsoleDialog();
		    	    dialog.setVisible(true);
		    	}
		    });
		}
		return menuBeanShell;
		
	}

	
	BeanShellConsoleDialog getBeanShellConsoleDialog() {
		if (beanShellConsoleDialog == null) {
			beanShellConsoleDialog = new BeanShellConsoleDialog(getView().getMainFrame(), false, this);
			beanShellConsoleDialog.setView(getView());
			beanShellConsoleDialog.setSize(600, 600);
			beanShellConsoleDialog.setTitle("BeanShell Console");
		}
		return beanShellConsoleDialog;
	}
	

}
