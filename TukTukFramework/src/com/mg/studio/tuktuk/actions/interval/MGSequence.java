package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.actions.bas.MGFiniteTimeAction;
import com.mg.studio.tuktuk.director.MGNode;

//
// Sequence
//

/** Runs actions sequentially, one after another
 */
public class MGSequence extends MGIntervalAction {
    private MGFiniteTimeAction[] actions;
    private float split;
    private int last;

    /** helper contructor to create an array of sequenceable actions */
    public static MGSequence actions(MGFiniteTimeAction action1, MGFiniteTimeAction... actions) {
        if(actions.length == 0) {
        	return new MGSequence(action1, MGFiniteTimeAction.action(0));
        } else {
	    	MGFiniteTimeAction prev = action1;
	        for (MGFiniteTimeAction now : actions) {
	            prev = new MGSequence(prev, now);
	        }
	        return (MGSequence) prev;
        }
    }
    
    /** initializes the action */
    protected MGSequence(MGFiniteTimeAction one, MGFiniteTimeAction two) {
        //assert one != null : "Sequence: argument one must be non-null";
        //assert two != null : "Sequence: argument two must be non-null";

        super(one.getDuration() + two.getDuration());

        actions = new MGFiniteTimeAction[2];
        actions[0] = one;
        actions[1] = two;
    }

    @Override
    public MGSequence copy() {
        return new MGSequence(actions[0].copy(), actions[1].copy());
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        split = actions[0].getDuration() / duration;
        last = -1;
    }

    public void stop() {
    	actions[0].stop();
        actions[1].stop();
        
        super.stop();
    }


    @Override
    public void update(float t) {
        int found = 0;
        float new_t = 0.f;

        if (t >= split) {
            found = 1;
            if (split == 1)
                new_t = 1;
            else
                new_t = (t - split) / (1 - split);
        } else {
            found = 0;
            if (split != 0)
                new_t = t / split;
            else
                new_t = 1;
        }

        if (last == -1 && found == 1) {
            actions[0].start(target);
            actions[0].update(1.0f);
            actions[0].stop();
        }

        if (last != found) {
            if (last != -1) {
                actions[last].update(1.0f);
                actions[last].stop();
            }
            actions[found].start(target);
        }
        actions[found].update(new_t);
        last = found;
    }

    @Override
    public MGSequence reverse() {
        return new MGSequence(actions[1].reverse(), actions[0].reverse());
    }
}
