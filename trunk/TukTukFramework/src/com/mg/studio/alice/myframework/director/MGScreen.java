package com.mg.studio.alice.myframework.director;

import android.graphics.PointF;

import com.mg.studio.alice.myframework.events.MGEventDelegateProtocol;

public class MGScreen extends MGNode implements MGEventDelegateProtocol {
    
	public static MGScreen node() {
		return new MGScreen();
	}

	protected MGScreen() {
		super();
		setRelativeAnchorPoint(false);
		setAnchorPoint(0, 0);
		setContentSize(CanvasGame.sizeDevices);
	}

	public void onPause() {
		
	}
	public void onResume() {
		
	}
	public void onDestroy() {
		
	}
	
	
	public boolean onBackPressed(){
		return false;
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
