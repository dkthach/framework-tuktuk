package com.mg.studio.tuktuk.actions.instant;

/**
 * Calls a 'callback' with the node as the first argument
 * N means Node
 */
public class MGCallFuncN extends MGCallFunc {

    public static MGCallFuncN action(Object t, String s) {
        return new MGCallFuncN(t, s);
    }

    /**
     * creates the action with the callback
     */
    protected MGCallFuncN(Object t, String s) {
        super(t, s);

        try {
            Class<?> cls = targetCallback.getClass();
            Class<?> partypes[] = new Class[] { Object.class };
            invocation = cls.getMethod(selector, partypes);
        } catch (NoSuchMethodException e) {
        }
    }

    /**
     * executes the callback
     */
    public void execute() {
        try {
            invocation.invoke(targetCallback, new Object[]{target});
        } catch (Exception e) {
        }
    }
}
