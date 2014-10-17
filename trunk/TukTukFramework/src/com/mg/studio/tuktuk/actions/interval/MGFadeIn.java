package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.events.MGRGBAProtocol;


/** Fades In an object that implements the MGRGBAProtocol protocol.
 * It modifies the opacity from 0 to 255.
 *  The "reverse" of this action is FadeOut
 */
public class MGFadeIn extends MGIntervalAction {

    public static MGFadeIn action(float t) {
        return new MGFadeIn(t);
    }

    protected MGFadeIn(float t) {
        super(t);
    }

	@Override
	public MGFadeIn copy() {
		return new MGFadeIn(duration);
	}

    @Override
    public void update(float t) {
        ((MGRGBAProtocol) target).setOpacity( (1f * t));
    }

    @Override
    public MGFadeOut reverse() {
        return new MGFadeOut(duration);
    }
}

