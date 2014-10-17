package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseBackOut extends MGEaseAction {
	 float overshoot;
    public static MGEaseBackOut action(MGIntervalAction action) {
        return new MGEaseBackOut(action);
    }

    protected MGEaseBackOut(MGIntervalAction action) {
        super(action);
        overshoot = 1.70158f;
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseBackOut(other.copy());
    }

    @Override
    public void update(float t) {
         
    

        t = t - 1;
        other.update(t * t * ((overshoot + 1) * t + overshoot) + 1);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseBackIn(other.reverse());
    }
    
public void setOvershoot(float overshoot) {
	this.overshoot = overshoot;
}
}
