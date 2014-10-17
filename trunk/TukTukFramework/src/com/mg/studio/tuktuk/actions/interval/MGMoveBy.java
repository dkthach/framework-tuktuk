package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.director.MGNode;
import com.mg.studio.tuktuk.type.MGPointF;



/**
 * @author Dk Thach
 *
 */

/**  Moves a MGNode object x,y pixels by modifying it's position attribute.
 x and y are relative to the position of the object.
 Duration is is seconds.
*/ 

public class MGMoveBy extends MGMoveTo {
    /** creates the action */
    public static MGMoveBy action(float duration, MGPointF pos) {
        return new MGMoveBy(duration, pos);
    }

    /** initializes the action */
    protected MGMoveBy(float t, MGPointF pos) {
        super(t, pos);
        delta.set(pos.x, pos.y);
    }

    @Override
    public MGMoveBy copy() {
        return new MGMoveBy(duration, delta);
    }

    @Override
    public void start(MGNode aTarget) {
    	float tmpx = delta.x;
		float tmpy = delta.y;

        super.start(aTarget);
        delta.set(tmpx, tmpy);
    }

    @Override
    public MGMoveBy reverse() {
        return new MGMoveBy(duration, MGPointF.ccpNeg(delta));
    }
}

