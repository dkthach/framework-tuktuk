package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.actions.ABSProgressTimer;
import com.mg.studio.alice.myframework.director.MGNode;


/**
 Progress from a percentage to another percentage

 */
public class MGProgressFromTo extends MGIntervalAction {
	float to_;
	float from_;

    /** Creates and initializes the action with a duration, a "from" percentage and a "to" percentage */
    public static MGProgressFromTo action(float t, float fromPercentage, float toPercentage) {
        return new MGProgressFromTo(t, fromPercentage, toPercentage);
    }

    /** Initializes the action with a duration, a "from" percentage and a "to" percentage */
    protected MGProgressFromTo(float t, float fromPercentage, float toPercentage) {
        super(t);
        to_ = toPercentage;
        from_ = fromPercentage;
    }

    @Override
    public MGProgressFromTo copy() {
        return new MGProgressFromTo(duration, from_, to_);
    }

    @Override
    public MGProgressFromTo reverse() {
        return new MGProgressFromTo(duration, to_, from_);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
    }

    @Override
    public void update(float t) {
        ((ABSProgressTimer)target).setPercentage(from_ + ( to_ - from_ ) * t);
    }
}

