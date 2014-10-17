package com.mg.studio.alice.myframework.actions.instant;

import com.mg.studio.alice.myframework.actions.bas.MGFiniteTimeAction;


/** Instant actions la action hoat dong ngay khi goi. No khong co  thoi gian
	nhu MGIntervalAction actions.
*/
public class MGInstantAction extends MGFiniteTimeAction {

    protected MGInstantAction() {
        super(0);
    }

    @Override
    public MGInstantAction copy() {
        return new MGInstantAction();
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void step(float dt) {
        update(1.0f);
    }

    @Override
    public void update(float t) {
        // ignore
    }

    @Override
    public MGFiniteTimeAction reverse() {
        return copy();
    }
}
