package com.mg.studio.alice.myframework.actions.instant;

import java.lang.reflect.Method;

import com.mg.studio.alice.myframework.director.MGNode;

//
// CallFunc
//

/**
 * Calls a 'callback'
 */
public class MGCallFunc extends MGInstantAction {
    protected Object targetCallback;
    protected String selector;

    protected Method invocation;

    /** creates the action with the callback */
    public static MGCallFunc action(Object target, String selector) {
        return new MGCallFunc(target, selector);
    }

    /**
     * creates an action with a callback
     */
    protected MGCallFunc(Object t, String s) {
        targetCallback = t;
        selector = s;

        try {
            Class<?> cls = targetCallback.getClass();
            invocation = cls.getMethod(selector);
        } catch (Exception e) {
        }
    }

    public MGCallFunc copy() {
        return new MGCallFunc(targetCallback, selector);
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
            invocation.invoke(targetCallback);
        } catch (Exception e) {
        }
    }
}
