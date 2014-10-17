package com.mg.studio.tuktuk.director;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;

import com.mg.studio.engine.MGColor;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.interval.MGFadeTo;
import com.mg.studio.tuktuk.actions.interval.MGSequence;
import com.mg.studio.tuktuk.layers.MGColorLayer;

/**
 * @author Dk Thạch
 * 
 */

public class MGDialogManager {
	private static MGDialogManager instance = null;
	private MGDialog availableDialog_;
	private MGDialog nextDialog_;
	protected float durationFadeInTransParent = 0.6F;
	protected float durationFadeOutTransParent = 0.6F;
	protected float opacityOfTransParentLayer = 0.7F;
	MGColorLayer transparentLayer = null;
	protected boolean hideTransParentLayer = false;
	/** ẩn diglog stack chi vẽ dialog avaiable **/
	protected boolean hideDiglogStack = true;

	List<MGDialog> dialogStack_;
	private boolean sendCleanupToDialog;

	private MGDialogManager() {
		init();
	}

	public static MGDialogManager shareManager() {
		if (instance == null) {
			instance = new MGDialogManager();
		}
		return instance;
	}

	/**
	 * @return size of dialog stack
	 */
	public int getNumberOfDialogOnstack() {
		if (dialogStack_ == null) {
			return 0;
		}
		return dialogStack_.size();
	}

	public boolean onBackPressed() {
		if (availableDialog_ != null) {
			return availableDialog_.onBackPressed();
		}
		return false;
	}

	public MGDialog getAvailableDialog() {
		return availableDialog_;
	}

	// ----
	/** gán true nếu ko muốn vẽ lớp transParent mặc định Fale ==>vẽ **/
	public void setHideTransParentLayer(boolean hideTransParentLayer) {
		this.hideTransParentLayer = hideTransParentLayer;
	}

	/** cho phép vẽ dialog stack hay ko **/
	public void setHideDiglogStack(boolean hideDiglogStack) {
		this.hideDiglogStack = hideDiglogStack;
	}

	// khởi tạo và chạy hiệu ứng fade in cho lớp màu transparent
	private void alocLazyAndFadeInTransparentLayer() {
		destroyTransParentLayer();
		if (transparentLayer == null) {
			transparentLayer = MGColorLayer.node(new MGColor(0f, 0, 0, 0));
			transparentLayer.onEnter();
			// tạo action fadeInTo
			MGFadeTo fadeInTo = MGFadeTo.action(durationFadeInTransParent,
					opacityOfTransParentLayer);
			transparentLayer.runAction(fadeInTo);
		}

	}

	/**
	 * cài dặt lại thời gian lớp transparent fadeIn mặc định 0.6 giây
	 **/
	public void setDurationFadeInTransParent(float durationFadeInTransParent) {
		this.durationFadeInTransParent = durationFadeInTransParent;
	}

	/**
	 * cài dặt lại thời gian lớp transparent fadeOut mặc định 0.6giây
	 **/
	public void setDurationFadeOutTransParent(float durationFadeOutTransParent) {
		this.durationFadeOutTransParent = durationFadeOutTransParent;
	}

	/** cài dặt lại Opacity lớp transparent mặc định 0.7 **/
	public void setOpacityOfTransParentLayer(float opacityOfTransParentLayer) {
		this.opacityOfTransParentLayer = opacityOfTransParentLayer;
	}

	// mờ dần lớp transparent cho đến 0
	public void fadeOutTransParentLayer() {
		if (this.transparentLayer != null) {
			this.transparentLayer.runAction(MGSequence.actions(
					MGFadeTo.action(durationFadeOutTransParent, 0),
					MGCallback.action(new ActionCallback() {
						@Override
						public void execute(Object object) {
							destroyTransParentLayer();
						}
					})));
		}
	}

	private void destroyTransParentLayer() {
		if (transparentLayer != null
				&& (!hideDiglogStack || getAvailableDialog() == null)) {
			transparentLayer.onExit();
			transparentLayer.cleanup();
			transparentLayer = null;
		}
	}

	// ---

	private void init() {
		availableDialog_ = null;
		nextDialog_ = null;
		dialogStack_ = new ArrayList<MGDialog>(10);

	}

	public void render(MGGraphic g) {
		if (nextDialog_ != null) {
			if (!hideTransParentLayer) {
				alocLazyAndFadeInTransparentLayer();
			}
			setNextDialog();

		}

		for (int i = 0; i < dialogStack_.size() - 1; i++) {
			try {
				dialogStack_.get(i).paintSelfAndChild(g);
			} catch (Throwable e) {
				// TODO: handle exception
			}

		}
		if (transparentLayer != null) {
			transparentLayer.paintSelfAndChild(g);
		}
		if (availableDialog_ != null) {
			availableDialog_.paintSelfAndChild(g);

		}

	}

	public boolean getSendCleanupToDialog() {
		return sendCleanupToDialog;
	}

	public void pushDialog(MGDialog dialog) {
		assert dialog != null : "dialog phai != Null";
		sendCleanupToDialog = false;
		dialogStack_.add(dialog);
		nextDialog_ = dialog;

	}

	public void clearAlldialog() {
		fadeOutTransParentLayer();
		end();

	}

	public void popDiaLog() {
		assert availableDialog_ != null : "ko co dialog truoc no";

		dialogStack_.remove(dialogStack_.size() - 1);
		int c = dialogStack_.size();

		if (c == 0) {
			end();
		} else {
			nextDialog_ = dialogStack_.get(c - 1);
		}

	}

	public void replaceDialog(MGDialog dialog) {
		assert dialog != null : "dialog phai != Null";

		int index = dialogStack_.size();
		sendCleanupToDialog = true;
		dialogStack_.set(index - 1, dialog);

		nextDialog_ = dialog;
	}

	private void end() {
		if (availableDialog_ != null) {
			availableDialog_.onExit();
			availableDialog_.cleanup();
			availableDialog_ = null;
		}
		nextDialog_ = null;
		dialogStack_.clear();
	}

	public void setNextDialog() {

		if (availableDialog_ != null) {
			//availableDialog_.onExit();
			if (hideDiglogStack) {
				availableDialog_.setHideOfShowWithAnimate(true);
			}
		}
		// nhan thong bao va don dep neu khong se bi ro ri memory
		if (sendCleanupToDialog) {
			availableDialog_.cleanup();
		}
		availableDialog_ = nextDialog_;
		nextDialog_ = null;
		
		availableDialog_.onEnter();
		if (hideDiglogStack&&availableDialog_.isHide()) {
			availableDialog_.setHideOfShowWithAnimate(false);
		}
	}

	public boolean onTouchDown1(PointF points) {
		if (availableDialog_ != null) {
			return availableDialog_.onTouchBegan(points);
		}
		return false;
	}

	public boolean onTouchDown2(PointF points) {
		if (availableDialog_ != null) {
			return false;
		}
		return false;
	}

	public boolean onTouchUp1(PointF pointF) {
		if (availableDialog_ != null) {
			return availableDialog_.onTouchEnd(pointF);
		}
		return false;
	}

	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr) {
		if (availableDialog_ != null) {
			return availableDialog_.onTouchMove(xPre, yPre, xCurr, yCurr);
		}
		return false;
	}

}
