package com.mg.studio.tuktuk.actions.instant;


/**
 * Calls a 'callback' with the node as the first argument and the 2nd argument is data
 * ND means: Node Data
 */
public class MGCallFuncND extends MGCallFuncN {
    protected Object data;

    /** creates the action with the callback and the data to pass as an argument */
    public static MGCallFuncND action(Object t, String s, Object d) {
        return new MGCallFuncND(t, s, d);
    }

    /**
     * creates the action with the callback and the data to pass as an argument
     */
    protected MGCallFuncND(Object t, String s, Object d) {
        super(t, s);
        data = d;

        try {
            Class<?> cls = targetCallback.getClass();
            Class<?> partypes[] = new Class<?>[] {
            	Object.class, Object.class,
            };
            invocation = cls.getMethod(selector, partypes);
        } catch (Exception e) {
        }
    }

    /**
     * executes the callback
     */
    public void execute() {
        try {
            invocation.invoke(targetCallback, new Object[]{target, data});
        } catch (Exception e) {
        }
    }
}
