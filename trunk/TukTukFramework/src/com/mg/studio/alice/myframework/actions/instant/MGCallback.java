package com.mg.studio.alice.myframework.actions.instant;

import com.mg.studio.alice.myframework.actions.ActionCallback;
import com.mg.studio.alice.myframework.director.MGNode;



public class MGCallback extends MGInstantAction {

	protected ActionCallback selector;

	/** creates the action with the callback */
	public static MGCallback action(ActionCallback callback) {
		return new MGCallback(callback);
	}

	/**
	 * creates an action with a callback
	 */
	protected MGCallback(ActionCallback callback) {

		selector = callback;
	}

	public MGCallback copy() {
		return new MGCallback(selector);
	}

	@Override
	public void start(MGNode aTarget) {
		super.start(aTarget);
		execute();
	}

	/**
	 * executes the callback 
	 */
	public void execute() {
		try {
			selector.execute(null);
		} catch (Throwable e) {
		}
	}
}
