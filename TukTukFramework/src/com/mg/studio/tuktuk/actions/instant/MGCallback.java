package com.mg.studio.tuktuk.actions.instant;

import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.director.MGNode;



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
