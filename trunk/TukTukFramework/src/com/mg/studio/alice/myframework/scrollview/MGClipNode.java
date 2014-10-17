package com.mg.studio.alice.myframework.scrollview;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.layers.MGLayer;
import com.mg.studio.alice.myframework.type.MGRect;
import com.mg.studio.engine.MGGraphic;

public class MGClipNode extends MGNode {
	public static final int RECT_ORIGIN_INVALID = 99999;
	MGRect _clippedRect;

	public MGClipNode() {
		_clippedRect = MGRect.make(RECT_ORIGIN_INVALID + 1, 0, 0, 0);
	}
	@Override
	public void paintSelfAndChild(MGGraphic g) {
		if (_clippedRect.origin.x < RECT_ORIGIN_INVALID) {
			
			g.setClip((int) _clippedRect.origin.x,
					(int) _clippedRect.origin.y,(int) _clippedRect.size.width,
					(int)_clippedRect.size.height);

		}
		super.paintSelfAndChild(g);
		if (_clippedRect.origin.x < RECT_ORIGIN_INVALID)
			g.clearClip();
	}

	public void setClipRect(MGRect clippedRect) {
		_clippedRect = clippedRect;
	}

}
