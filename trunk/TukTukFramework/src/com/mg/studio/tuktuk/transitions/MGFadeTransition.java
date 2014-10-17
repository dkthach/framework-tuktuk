package com.mg.studio.tuktuk.transitions;

import com.mg.studio.engine.MGColor;
import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.interval.MGFadeIn;
import com.mg.studio.tuktuk.actions.interval.MGFadeOut;
import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.actions.interval.MGSequence;
import com.mg.studio.tuktuk.director.MGScreen;
import com.mg.studio.tuktuk.layers.MGColorLayer;

/**
 * Fade Transition. Fade out the outgoing scene and then fade in the incoming
 * scene.
 */
public class MGFadeTransition extends MGTransitionScreen {
	MGColor color;

	/**
	 * creates the transition with a duration and with an RGB color alpha = 0
	 */
	public static MGFadeTransition transition(float t, MGScreen s, MGColor rgb) {
		return new MGFadeTransition(t, s, rgb);
	}

	/**
	 * creates the transition with a duration
	 */
	public static MGFadeTransition transition(float t, MGScreen s) {
		return new MGFadeTransition(t, s);
	}

	/**
	 * initializes the transition with a duration and with an RGB color alpha =
	 * 0
	 */
	public MGFadeTransition(float d, MGScreen s, MGColor rgb) {
		super(d, s);
		color = new MGColor(0, rgb.getRed(), rgb.getGreen(), rgb.getBlue());
	}

	/**
	 * initializes the transition with a duration
	 */
	public MGFadeTransition(float d, MGScreen s) {
		this(d, s, new MGColor(0f, 0f, 0f, 0f));
	}

	@Override
	public void onEnter() {
		super.onEnter();
		MGColorLayer l = MGColorLayer.node(color);
		inScreen.setHide(true);
		addChild(l, 2, KEY_SCREEN_FACE);
		MGIntervalAction a = MGSequence.actions(
				MGFadeIn.action(duration / 2),
				MGCallback.action( new ActionCallback() {

					@Override
					public void execute(Object object) {
						hideOutShowIn();
					}
				}), MGFadeOut.action(duration / 2),
				MGCallback.action( new ActionCallback() {

					@Override
					public void execute(Object object) {
						finish();
					}
				}));
		l.runAction(a);
	}

	@Override
	public void onExit() {
		super.onExit();
		removeChildByTag(KEY_SCREEN_FACE, false);
	}

}
