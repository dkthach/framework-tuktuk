package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.actions.ABSProgressTimer;
import com.mg.studio.tuktuk.director.MGNode;


/**
 Progress to percentage

*/
public class MGProgressTo extends MGIntervalAction 
{
	float to_;
	float from_;

    /** Creates and initializes with a duration and a percent */
    public static MGProgressTo action(float duration, float percent) {
        return new MGProgressTo(duration, percent);
    }

    /** Initializes with a duration and a percent */
    protected MGProgressTo(float duration, float percent) {
        super(duration);
        to_ = percent;
    }

    @Override
    public MGProgressTo copy() {
        return new MGProgressTo(duration, to_);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        from_ = ((ABSProgressTimer)target).getPercentage();

        // XXX: Is this correct ?
        // Adding it to support CCRepeat
        if( from_ == 100)
            from_ = 0;
    }

    @Override
    public void update(float t) {
        ((ABSProgressTimer)target).setPercentage(from_ + ( to_ - from_ ) * t);
    }

}

