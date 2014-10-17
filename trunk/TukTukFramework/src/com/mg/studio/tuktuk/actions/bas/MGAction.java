/**
 * 
 */
package com.mg.studio.tuktuk.actions.bas;

import com.mg.studio.tuktuk.director.MGNode;
import com.mg.studio.tuktuk.util.Copyable;


/**
 * @author Dk Thach
 *
 */
public abstract class MGAction implements Copyable{

	public static final int K_MG_ACTION_TAG_INVALIA = -1;

	    /** The "target". The action will modify the target properties.
	     The target will be set with the 'startWithTarget' method.
	     When the 'stop' method is called, target will be set to nil.
	     The target is 'assigned', it is not 'retained'.
	     */
	    public MGNode target;

	    /** The original target, since target can be nil.
	     Is the target that were used to run the action. Unless you are doing something complex, like ActionManager, you should NOT call this method.
	    */
	    private MGNode originalTarget;

	    /** The action tag. An identifier of the action */
	    private int tag;

	    public MGNode getOriginalTarget() {
	        return originalTarget;
	    }

	    public void setOriginalTarget(MGNode value) {
	        originalTarget = value;
	    }

	    public MGNode getTarget() {
	        return target;
	    }

	    public void setTarget(MGNode value) {
	        target = value;
	    }

	    public int getTag() {
	        return tag;
	    }

	    public void setTag(int value) {
	        tag = value;
	    }

	    /** Allocates and initializes the action */
	    public static MGAction action() {
	    	return null;
	    }

	    /** Initializes the action */
	    public MGAction() {
	        target = originalTarget = null;
	        tag = K_MG_ACTION_TAG_INVALIA;
	    }

	    public abstract MGAction copy();

	    //! called before the action start. It will also set the target.
	    public void start(MGNode aTarget) {
	        originalTarget = target = aTarget;
	    }

	    //! called after the action has finished. It will set the 'target' to nil.
	    //! IMPORTANT: You should never call "[action stop]" manually. Instead, use: "[target stopAction:action];"
	    public void stop() {
	        // target = null;
	    }

	    //! return YES if the action has finished
	    public boolean isDone() {
	        return true;
	    }

	    //! called every frame with it's delta time. DON'T override unless you know what you are doing.
	    public abstract void step(float dt);

	    //! called once per frame. time a value between 0 and 1
	    //! For example: 
	    //! * 0 means that the action just started
	    //! * 0.5 means that the action is in the middle
	    //! * 1 means that the action is over
	    public abstract void update(float time);

}
