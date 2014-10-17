package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


/** Ease Elastic abstract class
 
 */
public abstract class MGEaseElastic extends MGEaseAction {
	/** period of the wave in radians. default is 0.3 */
	protected float period_;

	/** Initializes the action with the inner action 
	 * 	and the period in radians (default is 0.3) */
    protected MGEaseElastic(MGIntervalAction action, float period) {
        super(action);
        period_ = period;
    }
    
    protected MGEaseElastic(MGIntervalAction action) {
    	this(action, 0.3f);
    }

    @Override
    public abstract MGEaseAction copy();

    @Override
    public abstract MGIntervalAction reverse();
}
