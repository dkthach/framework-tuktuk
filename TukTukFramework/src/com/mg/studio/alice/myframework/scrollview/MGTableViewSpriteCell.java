package com.mg.studio.alice.myframework.scrollview;

import com.mg.studio.alice.myframework.type.MGPointF;



public class MGTableViewSpriteCell extends MGTableViewCell {
	private SpriteNode m_sprite;

	public void setSprite(SpriteNode s) {
	    if (m_sprite != null) {
	        removeChild(m_sprite, false);
	    }
	    
	    s.setAnchorPoint(MGPointF.zero());
	    s.setPosition(MGPointF.zero());
	    m_sprite = s;
	    addChild(m_sprite);
	}
	
	public SpriteNode getSprite() {
	    return m_sprite;
	}
	
	@Override
	public void reset() {
		super.reset();
	
	    if (m_sprite != null) {
	        removeChild(m_sprite, false);
	    }
	    m_sprite = null;
	}
}
