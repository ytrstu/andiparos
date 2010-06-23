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
package org.parosproxy.paros.model;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SiteNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -488214040888591573L;
	
	private String nodeName = null;
	private HistoryReference historyReference = null;
	private Vector<HistoryReference> pastHistoryList = new Vector<HistoryReference>(10);

	public SiteNode(String nodeName) {
		super();
		this.nodeName = nodeName;
	}

	public String toString() {
		return nodeName;
	}

	public HistoryReference getHistoryReference() {
		return historyReference;
	}

	/**
	 * Set current node reference. If there is any existing reference, delete if
	 * spider record. Otherwise, put into past history list.
	 * 
	 * @param historyReference
	 */
	public void setHistoryReference(HistoryReference historyReference) {

		if (getHistoryReference() != null) {
			if (!getPastHistoryReference().contains(historyReference)) {
				getPastHistoryReference().add(getHistoryReference());
			}
		}

		this.historyReference = historyReference;
		this.historyReference.setSiteNode(this);
	}

	public Vector<HistoryReference> getPastHistoryReference() {
		return pastHistoryList;
	}

}
