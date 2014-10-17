package com.mg.studio.alice.myframework.actions.interval;

import android.graphics.PointF;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.type.MGPointF;

//
// MoveTo
//

/** Moves a MGNode object to the position x,y. x and y are absolute coordinates by modifying it's position attribute.
*/
public class MGMoveTo extends MGIntervalAction {
    private PointF endPosition;
    private MGPointF startPosition;
    protected MGPointF delta;

    /** creates the action */
    public static MGMoveTo action(float t, MGPointF pos) {
        return new MGMoveTo(t, pos);
    }

    /** initializes the action */
    protected MGMoveTo(float t, PointF pos) {
        super(t);
        startPosition = MGPointF.zero();
        endPosition = new PointF(pos.x, pos.y);
        delta = new MGPointF();
    }
    
    /**
     * Lets extend basic functionality for reuse action.
     */
    public void setEndPosition(PointF pos) {
    	endPosition.set(pos);
    }

    @Override
    public MGIntervalAction copy() {
        return new MGMoveTo(duration, endPosition);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);

        startPosition.set(target.getPosition());
        delta.set(endPosition.x - startPosition.x, endPosition.y - startPosition.y);
    }

    @Override
    public void update(float t) {
        target.setPosition(startPosition.x + delta.x * t,
        					startPosition.y + delta.y * t);
    }
}
