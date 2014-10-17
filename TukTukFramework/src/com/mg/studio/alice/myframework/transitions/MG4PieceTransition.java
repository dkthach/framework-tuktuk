package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.myframework.actions.ActionCallback;
import com.mg.studio.alice.myframework.actions.ease.MGEaseExponentialOut;
import com.mg.studio.alice.myframework.actions.instant.MGCallback;
import com.mg.studio.alice.myframework.actions.interval.MGDelayTime;
import com.mg.studio.alice.myframework.actions.interval.MGFadeTo;
import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;
import com.mg.studio.alice.myframework.actions.interval.MGJumpTo;
import com.mg.studio.alice.myframework.actions.interval.MGRotateBy;
import com.mg.studio.alice.myframework.actions.interval.MGScaleTo;
import com.mg.studio.alice.myframework.actions.interval.MGSequence;
import com.mg.studio.alice.myframework.actions.interval.MGSpawn;
import com.mg.studio.alice.myframework.director.CanvasGame;
import com.mg.studio.alice.myframework.director.MGScreen;
import com.mg.studio.alice.myframework.layers.MGColorLayer;
import com.mg.studio.alice.myframework.layers.TransEffectLayer;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.engine.MGColor;

/**
 * Fade Transition. Fade out the outgoing scene and then fade in the incoming
 * scene.
 */
public class MG4PieceTransition extends MGTransitionScreen {

	/**
	 * creates the transition with a duration and with an RGB color alpha = 0
	 */
	public static MG4PieceTransition transition(float t, MGScreen s) {
		return new MG4PieceTransition(t, s);
	}

	/**
	 * initializes the transition with a duration and with an RGB color alpha =
	 * 0
	 */
	public MG4PieceTransition(float d, MGScreen s) {
		super(d, s);

	}

	@Override
	public void onEnter() {
		super.onEnter();
		final MGColorLayer colorLayer = MGColorLayer.node(new MGColor(0.8f,
				0, 0, 0));
		colorLayer.setPosition(0, 0);
		addChild(colorLayer, 1);
		final MGFadeTo fadeTo = MGFadeTo.action(duration / 3, 0);
		 MGFadeTo fadeTrans = MGFadeTo.action(duration / 4, 0);
		TransEffectLayer l = new TransEffectLayer();
		l.setPosition(-l.getContentSize().width / 2,
				-l.getContentSize().height / 2);
		inScreen.setHide(true);
		inScreen.onExit();
		addChild(l, 2, KEY_SCREEN_FACE);
		l.setScale(5);
		l.setOpacity(1);

		MGJumpTo moveToCenter = MGJumpTo.action(duration / 2, new MGPointF(
				CanvasGame.widthDevices / 2, CanvasGame.heightDevices / 2),
				(int) CanvasGame.heightDevices / 2, 1);
		MGScaleTo zoomOut = MGScaleTo.action(duration / 2, 0.5f);
		MGRotateBy roto = MGRotateBy.action(duration / 2, -720);

		MGIntervalAction a = MGSequence
				.actions(moveToCenter, MGCallback
						.action(new ActionCallback() {
							@Override
							public void execute(Object object) {
								hideOutShowIn();
							}
						}), MGEaseExponentialOut.action(MGSpawn.actions(
						zoomOut, roto)), MGDelayTime.action(0.6f),
						MGSpawn.actions(
								MGCallback.action(new ActionCallback() {

									@Override
									public void execute(Object object) {
										colorLayer.runAction(MGSequence
												.actions(fadeTo,
														MGCallback.action(new ActionCallback() {

															@Override
															public void execute(
																	Object object) {
																finish();
																inScreen.onEnter();

															}
														})));

									}
								}), fadeTrans));
		l.runAction(a);
	}

	@Override
	public void onExit() {
		super.onExit();
		removeChildByTag(KEY_SCREEN_FACE, false);
	}

}
