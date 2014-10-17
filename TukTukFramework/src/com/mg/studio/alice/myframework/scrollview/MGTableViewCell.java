/*
 *	Port from SWScrollView and SWTableView for iphone 
 *	by Rodrigo Collavo on 02/03/2012
 */

package com.mg.studio.alice.myframework.scrollview;

import com.mg.studio.alice.myframework.director.MGNode;

public class MGTableViewCell extends MGNode {
	private int m_idx;
		
	public void reset() {
	    m_idx = Integer.MAX_VALUE;
	}
	    
	public void setObjectID(int i) {
	    m_idx = i;
	}
	    
	public int getObjectID() {
	    return m_idx;
	}

}
