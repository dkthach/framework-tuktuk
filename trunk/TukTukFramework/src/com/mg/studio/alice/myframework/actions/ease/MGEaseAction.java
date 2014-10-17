package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;
import com.mg.studio.alice.myframework.director.MGNode;




/**
 * @author Dk Thach
 *
 */
/** Base class for Easing actions
 */
public class MGEaseAction extends MGIntervalAction {
	public static final float M_PI_X_2 = (float) (Math.PI * 2.0f);
	
    protected MGIntervalAction other;

    /** creates the action */
    public static MGEaseAction action(MGIntervalAction action) {
    	return new MGEaseAction(action);
    }

    /** initializes the action */
    protected MGEaseAction(MGIntervalAction action) {
    	super(action.getDuration());
        other = action;
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseAction(other.copy());
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        other.start(target);
    }

    @Override
    public void stop() {
        other.stop();
        super.stop();
    }

    @Override
    public void update(float t) {
        other.update(t);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseAction(other.reverse());
    }

}
