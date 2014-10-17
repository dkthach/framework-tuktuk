package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.actions.bas.MGFiniteTimeAction;
import com.mg.studio.alice.myframework.config.MGMacros;
import com.mg.studio.alice.myframework.director.MGNode;


/** An interval action is an action that takes place within a certain period of time.
It has an start time, and a finish time. The finish time is the parameter
duration plus the start time.

These MGIntervalAction actions have some interesting properties, like:
 - They can run normally (default)
 - They can run reversed with the reverse method
 - They can run with the time altered with the Accelerate, AccelDeccel and Speed actions.

For example, you can simulate a Ping Pong effect running the action normally and
then running it again in Reverse mode.


*/
public class MGIntervalAction extends MGFiniteTimeAction {
	/** how many seconds had elapsed since the actions started to run. */
    protected float elapsed;
	private boolean firstTick;

    public float getElapsed() {
        return elapsed;
    }
    
    /** creates the action */
    public static MGIntervalAction action(float duration) {
    	return new MGIntervalAction(duration);
    }

    /** initializes the action */
    protected MGIntervalAction(float d) {    	
        super(d);
        if (duration == 0)
    		duration = MGMacros.FLT_EPSILON;
        elapsed = 0.0f;
        firstTick = true;
    }

    @Override
    public MGIntervalAction copy() {
        return new MGIntervalAction(duration);
    }

	/** returns YES if the action has finished */
    @Override
    public boolean isDone() {
        return (elapsed >= duration);
    }

    @Override
    public void step(float dt) {
        if (firstTick) {
            firstTick = false;
            elapsed = 0;
        } else
            elapsed += dt;

        update(Math.min(1, elapsed / duration));
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        elapsed = 0.0f;
        firstTick = true;
    }

	/** returns a reversed action */
    @Override
    public MGIntervalAction reverse() {
        assert false:("Reverse action not implemented");
    	return null;
    }
    
    public void setAmplitudeRate(float amp) {
    	assert false:"IntervalAction (Amplitude): Abstract class needs implementation";
    }

    public float getAmplitudeRate() {
    	assert (false) :"IntervalAction (Amplitude): Abstract class needs implementation";
    	return 0;
    }
}
