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
package org.parosproxy.paros.extension.update;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JMenuItem;

import org.apache.commons.httpclient.URI;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpSender;
import org.parosproxy.paros.network.HttpStatusCode;
import org.parosproxy.paros.view.WaitMessageDialog;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class ExtensionUpdate extends ExtensionAdaptor {

	private JMenuItem menuItemCheckUpdate = null;
	
	private static final String ANDIPAROS_FILES = "http://code.google.com/p/andiparos/downloads/list";
	private HttpSender httpSender = null;
	
	private Pattern patternNewestVersionArchive = Pattern.compile("Andiparos-v(\\d+)\\.(\\d+)\\.tar\\.gz", Pattern.MULTILINE);
	private Pattern patternNewestVersionMacOsX = Pattern.compile("Andiparos-v(\\d+)\\.(\\d+)\\.dmg", Pattern.MULTILINE);


	String newestVersionName = null;
	private boolean manualCheckStarted = false;


	private WaitMessageDialog waitDialog = null;

	/**
     * 
     */
	public ExtensionUpdate() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setName("ExtensionUpdate");

	}

	/**
	 * This method initializes menuItemEncoder
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemCheckUpdate() {
		if (menuItemCheckUpdate == null) {
			menuItemCheckUpdate = new JMenuItem();
			menuItemCheckUpdate.setText("Check for updates...");
			if (!Constant.isWindows() && !Constant.isLinux() && !Constant.isOSX()) {
				menuItemCheckUpdate.setEnabled(false);
			}
			menuItemCheckUpdate.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Thread t = new Thread(new Runnable() {
						public void run() {
							manualCheckStarted = true;
							newestVersionName = getNewestVersionName();

							if (waitDialog != null) {
								waitDialog.setVisible(false);
								waitDialog = null;
							}
							EventQueue.invokeLater(new Runnable() {
								public void run() {

									if (newestVersionName == null) {
										getView().showMessageDialog("Sorry, no update available.");
									} else if (newestVersionName.equals("")) {
										getView().showWarningDialog("Error encountered. Please check manually for new updates.");
									} else {
										newestVersionName = newestVersionName.replaceAll("\\.tar\\.gz", "");
										newestVersionName = newestVersionName.replaceAll("\\.dmg", "");
										getView().showMessageDialog("A new version of "
											+ Constant.PROGRAM_NAME + " is available: "
											+ newestVersionName + "\nPlease update!");
									}

								}
							});
						}
					});
					
					waitDialog = getView().getWaitMessageDialog("Checking if newer version exists...");
					t.start();
					waitDialog.setVisible(true);
				}
			});
		}
		return menuItemCheckUpdate;
	}

	public void start() {


		// check 1 in 30 cases to avoid too frequent check.
		if (getRandom(30) != 1) {
			return;
		}

		Thread t = new Thread(new Runnable() {
			public void run() {

				newestVersionName = getNewestVersionName();
				if (newestVersionName == null || newestVersionName.length() == 0) {
					return;
				} else {
					ExtensionUpdate.this.showUpdateMessage(true);
				}
			}

		});
		t.start();

	}

	public void hook(ExtensionHook extensionHook) {
		super.hook(extensionHook);
		if (getView() != null) {
			extensionHook.getHookMenu().addToolsMenuItem(getMenuItemCheckUpdate());
		}
	}

	public void showUpdateMessage(final boolean silent) {

		if (newestVersionName == null) {
			return;
		}

		String s = "A newer version of " + Constant.PROGRAM_NAME + " has been Found. Feel free to update.";
		getView().showMessageDialog(s);
	}

	private String getNewestVersionName() {
		String newVersionName = null;
		HttpMessage msg = null;
		String resBody = null;

		try {
			msg = new HttpMessage(new URI(ANDIPAROS_FILES, true));
			getHttpSender().sendAndReceive(msg, true);
			if (msg.getResponseHeader().getStatusCode() != HttpStatusCode.OK) {
				throw new IOException();
			}
			resBody = msg.getResponseBody().toString();
			
			Matcher matcher = null;
			
			if (Constant.isOSX()) {
				matcher = patternNewestVersionMacOsX.matcher(resBody);
			} else {
				matcher = patternNewestVersionArchive.matcher(resBody);
			}
			
			if (matcher.find()) {
				int ver_major = Integer.parseInt(matcher.group(1));
				int ver_minor = Integer.parseInt(matcher.group(2));
				long version = 100000 * ver_major + 100 * ver_minor;
				if (version > Constant.VERSION_TAG) {
					newVersionName = matcher.group(0);
				}
			}
		} catch (Exception e) {
			newVersionName = "";
		} finally {
			httpSender.shutdown();
			httpSender = null;
		}

		return newVersionName;
	}

	private HttpSender getHttpSender() {
		if (httpSender == null) {
			httpSender = new HttpSender(getModel().getOptionsParam().getConnectionParam(), true);
		}
		return httpSender;
	}

	private int getRandom(int max) {
		int result = (int) (max * Math.random());
		return result;
	}
}
