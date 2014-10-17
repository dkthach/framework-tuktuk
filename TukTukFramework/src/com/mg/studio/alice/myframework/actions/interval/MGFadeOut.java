package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.events.MGRGBAProtocol;

/** Fades Out an object that implements the CCRGBAProtocol protocol.
 * It modifies the opacity from 255 to 0.
 * The "reverse" of this action is FadeIn
*/
public class MGFadeOut extends MGIntervalAction {

    public static MGFadeOut action(float t) {
        return new MGFadeOut(t);
    }

    protected MGFadeOut(float t) {
        super(t);
    }

	@Override
	public MGFadeOut copy() {
		return new MGFadeOut(duration);
	}

    @Override
    public void update(float t) {
        ((MGRGBAProtocol) target).setOpacity((1f * (1 - t)));
    }

    @Override
    public MGFadeIn reverse() {
        return new MGFadeIn(duration);
    }
}
