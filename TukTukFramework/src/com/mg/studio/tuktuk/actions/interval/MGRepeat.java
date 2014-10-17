package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.actions.bas.MGFiniteTimeAction;
import com.mg.studio.tuktuk.director.MGNode;



/**
 * @author Dk Thach
 * Lap lai 1 action trong 1 khoan thoi gian cho truoc
 *
 */
public class MGRepeat extends MGIntervalAction {
    private int times;
    private int total;
    private MGFiniteTimeAction other;

    /** Times (t) tu 1 den pow(2,30) */
    public static MGRepeat action(MGFiniteTimeAction action, int t) {
        return new MGRepeat(action, t);
    }

    
    protected MGRepeat(MGFiniteTimeAction action, int t) {
        super(action.getDuration() * t);

        times = t;
        other = action;

        total = 0;
    }

    @Override
    public MGIntervalAction copy() {
        return new MGRepeat(other.copy(), times);
    }

    @Override
    public void start(MGNode aTarget) {
        total = 0;
        super.start(aTarget);
        other.start(aTarget);
    }

    public void stop() {

        other.stop();
        super.stop();
    }

 

    @Override
    public void update(float dt) {
        float t = dt * times;
        if (t > total + 1) {
            other.update(1.0f);
            total++;
            other.stop();
            other.start(target);
    		// repeat is over ?
    		if( total== times)
    			// so, set it in the original position
    			other.update(0);
    		else {
    			// no ? start next repeat with the right update
    			// to prevent jerk 
    			other.update(t-total);
    		}

        } else {
            float r = t % 1.0f;
            // fix last repeat position
            // else it could be 0.
            if (dt == 1.0f) {
                r = 1.0f;
                total ++;
            }
            other.update(Math.min(r, 1));
        }
    }

    @Override
    public boolean isDone() {
        return (total == times);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGRepeat(other.reverse(), times);
    }
}
