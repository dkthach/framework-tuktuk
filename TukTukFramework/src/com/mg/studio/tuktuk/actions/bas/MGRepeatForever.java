package com.mg.studio.tuktuk.actions.bas;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.director.MGNode;



/** Repeats an action for ever.
 To repeat the an action for a limited number of times use the Repeat action.
 @warning This action can't be Sequenceable because it is not an IntervalAction
 */
public class MGRepeatForever extends MGAction {
    protected MGIntervalAction other;

    /** creates the action */
    public static MGRepeatForever action(MGIntervalAction action) {
        return new MGRepeatForever(action);
    }

    /** initializes the action */
    protected MGRepeatForever(MGIntervalAction action) {
        other = action;
    }

    @Override
    public MGAction copy() {
        return new MGRepeatForever(other.copy());
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        other.start(target);
    }

    @Override
    public void step(float dt) {
        other.step(dt);
        if (other.isDone()) {
            float diff = dt + other.duration - other.getElapsed();
        	other.start(target);
        	other.step(diff);
        }
    }

    @Override
    public boolean isDone() {
        return false;
    }

    public MGRepeatForever reverse() {
        return MGRepeatForever.action(other.reverse());
    }

	@Override
	public void update(float time) {
		// TODO Auto-generated method stub
		
	}
}
