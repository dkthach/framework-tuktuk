package com.mg.studio.alice.myframework.actions.instant;

import com.mg.studio.alice.myframework.actions.ActionCallback;

public class MGCallbackN extends MGCallback {
	protected Object targetCallback;

	protected MGCallbackN(Object t, ActionCallback callback) {
		super(callback);
		targetCallback = t;

	}

	// ** creates the action with the callback *//*
	public static MGCallbackN action(Object target, ActionCallback callback) {
		return new MGCallbackN(target, callback);
	}

	public MGCallbackN copy() {
		return new MGCallbackN(targetCallback, selector);
	}

	public void execute() {
		try {
			selector.execute(targetCallback);
		} catch (Throwable e) {
		}
	}
}
