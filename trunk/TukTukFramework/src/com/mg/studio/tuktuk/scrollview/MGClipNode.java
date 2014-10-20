package com.mg.studio.tuktuk.scrollview;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.tuktuk.director.MGNode;
import com.mg.studio.tuktuk.type.MGRect;

public class MGClipNode extends MGNode {
	public static final int RECT_ORIGIN_INVALID = 99999;
	MGRect _clippedRect;

	public MGClipNode() {
		_clippedRect = MGRect.make(RECT_ORIGIN_INVALID + 1, 0, 0, 0);
	}
//	@Override
//	public void paintSelfAndChild(MGGraphic g) {
//		if (_clippedRect.origin.x < RECT_ORIGIN_INVALID) {
//			g.setClip((int) _clippedRect.origin.x,
//					(int) _clippedRect.origin.y,(int) _clippedRect.size.width,
//					(int)_clippedRect.size.height);
//
//		}
//		super.paintSelfAndChild(g);
//		if (_clippedRect.origin.x < RECT_ORIGIN_INVALID)
//			g.clearClip();
//	}

	public void setClipRect(MGRect clippedRect) {
		_clippedRect = clippedRect;
	}

}
