/**
 * 
 */
package com.mg.studio.tuktuk.layers;

import android.graphics.PointF;

import com.mg.studio.tuktuk.director.CanvasGame;
import com.mg.studio.tuktuk.director.MGNode;
import com.mg.studio.tuktuk.events.MGEventDelegateProtocol;

/**
 * @author Dk Thach
 *
 */
public class MGLayer extends MGNode implements MGEventDelegateProtocol{

	public static MGLayer node() {
		return new MGLayer();
	}

	protected MGLayer() {
		super();
		setRelativeAnchorPoint(false);
		setAnchorPoint(0, 0);
		setContentSize(CanvasGame.sizeDevices);
	}

	@Override
	public boolean onTouchDown1(PointF points) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchDown2(PointF points) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchUp1(PointF pointF) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchUp2(PointF pointF) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchDrag2(float xPre, float yPre, float xCurr,
			float yCurr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr, long eventDuration) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchDrag2(float xPre, float yPre, float xCurr,
			float yCurr, long eventDuration) {
		// TODO Auto-generated method stub
		return false;
	}

}
