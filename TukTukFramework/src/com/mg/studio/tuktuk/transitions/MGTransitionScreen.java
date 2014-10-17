/**
 * 
 */
package com.mg.studio.tuktuk.transitions;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.tuktuk.actions.UpdateCallback;
import com.mg.studio.tuktuk.director.MGDirector;
import com.mg.studio.tuktuk.director.MGScreen;

/**
 * @author Dk Thach
 * 
 */
public class MGTransitionScreen extends MGScreen {
	protected MGScreen inScreen;
	protected MGScreen outScreen;
	protected float duration;
	protected boolean inScreenOnTop;
	protected boolean sendCleanupToScreen;
	protected static final int KEY_SCREEN_FACE = 0xFADEFADE;

	protected MGTransitionScreen(float t, MGScreen s) {
		assert s != null : "screen can phai != null";
		duration = t;
		inScreen = s;
		outScreen = MGDirector.shareDirector().getRunningScreen();
		if (inScreen == outScreen) {
			throw new TransitionWithInvalidScreenException(
					"inScreen phai khac outScreen");
		}

		screenOrder();
	}

	protected void screenOrder() {
		inScreenOnTop = true;
	}

	@Override
	public void drawRearChild(MGGraphic g) {
		if (inScreenOnTop) {
			outScreen.paintSelfAndChild(g);
			inScreen.paintSelfAndChild(g);
		} else {
			inScreen.paintSelfAndChild(g);
			outScreen.paintSelfAndChild(g);
		}
	}

	@Override
	public void onEnter() {
		super.onEnter();
		inScreen.onEnter();
	}

	@Override
	public void onExit() {
		super.onExit();
		outScreen.onExit();
		inScreen.onEnterTransitionDidFinish();
	}

	private UpdateCallback setNewSceneCallback = new UpdateCallback() {

		@Override
		public void update(float d) {
			setNewSceen(d);

		}
	};

	// sau khi chay cac hieu ung tra lai binh thuong bang ham nay
	public void finish() {
		/* clean up */
		inScreen.setHide(false);
		inScreen.setPosition(0, 0);
		inScreen.setScale(1.0f);
		inScreen.setRotation(0.0f);
		

		outScreen.setHide(true);
		outScreen.setPosition(0, 0);
		outScreen.setScale(1.0f);
		outScreen.setRotation(0.0f);
		
		schedule(setNewSceneCallback);
	}

	public void setNewSceen(float dt) {
		unschedule(setNewSceneCallback);
		sendCleanupToScreen = MGDirector.shareDirector()
				.getSendCleanupToScreen();
		MGDirector.shareDirector().replaceScreen(inScreen);
		outScreen.setHide(true);
	}

	public void hideOutShowIn() {
		inScreen.setHide(false);
		outScreen.setHide(true);
	}

	// custom cleanup
	@Override
	public void cleanup() {
		super.cleanup();
		if (sendCleanupToScreen) {
			outScreen.cleanup();
		}
	}

	static class TransitionWithInvalidScreenException extends RuntimeException {
		public TransitionWithInvalidScreenException(String reason) {
			super(reason);
		}
	}

	public static MGTransitionScreen transition(float t, MGScreen s) {
		return new MGTransitionScreen(t, s);
	}

}
