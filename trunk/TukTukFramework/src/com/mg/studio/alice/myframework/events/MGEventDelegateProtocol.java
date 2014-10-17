package com.mg.studio.alice.myframework.events;

import android.graphics.PointF;

/**
 * @author Dk Thach
 *
 */
public interface MGEventDelegateProtocol {

	public boolean onTouchDown1(PointF points);

	public boolean onTouchDown2(PointF points);

	public boolean onTouchUp1(PointF pointF);

	public boolean onTouchUp2(PointF pointF);

	public boolean onTouchDrag1(float xPre, float yPre, float xCurr, float yCurr);

	public boolean onTouchDrag2(float xPre, float yPre, float xCurr, float yCurr);

	public boolean onTouchDrag1(float xPre, float yPre, float xCurr, float yCurr,
			long eventDuration);

	public boolean onTouchDrag2(float xPre, float yPre, float xCurr, float yCurr,
			long eventDuration);

}
