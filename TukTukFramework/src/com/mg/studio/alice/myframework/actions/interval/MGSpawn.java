package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.actions.bas.MGFiniteTimeAction;
import com.mg.studio.alice.myframework.director.MGNode;

/** Spawn a new action immediately
 */
//
// Spawn
//
public class MGSpawn extends MGIntervalAction {

    private MGFiniteTimeAction one;
    private MGFiniteTimeAction two;

    /** helper constructor to create an array of spawned actions */
    public static MGSpawn actions(MGFiniteTimeAction action1, MGFiniteTimeAction... params) {
        MGFiniteTimeAction prev = action1;

        if (action1 != null) {
            for (MGFiniteTimeAction now : params)
                prev = new MGSpawn(prev, now);
        }
        return (MGSpawn) prev;
    }
    
    /** initializes the Spawn action with the 2 actions to spawn */
    protected MGSpawn(MGFiniteTimeAction one_, MGFiniteTimeAction two_) {
        // assert one != null : "Spawn: argument one must be non-null";
        // assert two != null : "Spawn: argument two must be non-null";

        super(Math.max(one_.getDuration(), two_.getDuration()));

        float d1 = one_.getDuration();
        float d2 = two_.getDuration();

        one = one_;
        two = two_;

        if (d1 > d2)
            two = new MGSequence(two_, new MGDelayTime(d1 - d2));
        else if (d1 < d2)
            one = new MGSequence(one_, new MGDelayTime(d2 - d1));
    }

    @Override
    public MGIntervalAction copy() {
        return new MGSpawn(one.copy(), two.copy());
    }


    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        one.start(target);
        two.start(target);
    }

    @Override
    public void stop() {
        one.stop();
        two.stop();
        super.stop();
    }

    @Override
    public void update(float t) {
        one.update(t);
        two.update(t);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGSpawn(one.reverse(), two.reverse());
    }
}
