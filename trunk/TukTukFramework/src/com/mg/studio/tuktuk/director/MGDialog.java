package com.mg.studio.tuktuk.director;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;

import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.ease.MGEaseBackIn;
import com.mg.studio.tuktuk.actions.ease.MGEaseExponentialOut;
import com.mg.studio.tuktuk.actions.ease.MGEaseSineOut;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.interval.MGDelayTime;
import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.actions.interval.MGMoveTo;
import com.mg.studio.tuktuk.actions.interval.MGScaleTo;
import com.mg.studio.tuktuk.actions.interval.MGSequence;
import com.mg.studio.tuktuk.actions.interval.MGSpawn;
import com.mg.studio.tuktuk.type.MGPointF;
import com.mg.studio.tuktuk.type.MGSize;

public class MGDialog extends MGNode implements IDialogAction,
		MGDiaLogGLInterface {
	public final static int CLOSE_BUTTON_ID = 0XADAE0;
	public final static int CANCEL_BUTTON_ID = 0XDAEF1;
	public final static int YES_BUTTON_ID = 0XEDA2;
	protected MGPointF posToEnd;
	protected int tag_actionComming = 0xdea;
	OnClickListener onClickListener = null;
	OnDismissListener onDismissListener = null;
	OnCancelListener onCancelListener = null;
	OnShowListener onShowListener = null;
	OnDestroyListener onDestroyListener = null;
	protected boolean canceledOnTouchOutside = true;
	protected boolean canceledOnBackPressed = true;
	// cho bat su kiem o duoi dialog hay khong
	protected boolean onlyListenerEvent = true;

	protected MGButton yesButton = null;
	protected MGButton cancelButton = null;
	protected MGButton closeButton = null;
	List<MGButton> listButtons = null;
	protected float delay;
	MGDelayTime delayForShow = null;
	private boolean isTouchOutSize;

	protected MGDialog() {
		super();
		init();
	}

	protected void init() {
		posToEnd = new MGPointF();
		setAnchorPoint(0.5f, 0.5f);
	}

	private void alocListButtonlazy() {
		if (listButtons == null) {
			listButtons = new ArrayList<MGButton>();
		}
	}

	// cac button mac dinh

	public void setYesButton(MGButton yesButton, MGPointF position) {
		this.yesButton = yesButton;
		addMoreButton(yesButton, YES_BUTTON_ID, position);
	}

	public void setCloseButtonDefaulPosition(MGButton closeButton) {
		setCloseButton(closeButton, MGPointF.make(contentSize_.width, 0));
	}

	public void setCloseButton(MGButton closeButton, MGPointF position) {
		this.closeButton = closeButton;
		addMoreButton(closeButton, CLOSE_BUTTON_ID, position);
	}

	public void setCancelButton(MGButton cancelButton, MGPointF position) {
		this.cancelButton = cancelButton;
		addMoreButton(cancelButton, CANCEL_BUTTON_ID, position);
	}

	// them button moi
	public void addMoreButton(MGButton button, int id, MGPointF position) {
		button.setId(id);
		button.setPosition(position);
		addChild(button, 0);
		alocListButtonlazy();
		listButtons.add(button);
	}

	protected void setDefaulPosition(MGSize sizeDevices) {

		setPosition(sizeDevices.width / 2 - contentSize_.width / 2,
				sizeDevices.height / 2);
	}

	/** show dialog ở giữa màn hình với Position mặc định **/
	public void showWithDefaulStartPos() {
		MGSize s = CanvasGame.sizeDevices;
		setDefaulPosition(s);
		MGPointF center = new MGPointF(s.width / 2, s.height / 2);
		showAddtoStack(center);
	}

	/**
	 * show dialog ở giữa màn hình bắt đầu chạy từ Position start mới set lại
	 * khi tạo diaLog
	 **/
	public void showWithNewStartPos() {
		MGSize s = CanvasGame.sizeDevices;
		MGPointF center = new MGPointF(s.width / 2, s.height / 2);
		showAddtoStack(center);
	}

	/**
	 * show dialog ở target position ,bắt đầu chạy từ Position start mới set
	 * lại khi tạo diaLog đến targetpos
	 **/
	public void show(MGPointF targetPos) {
		showAddtoStack(targetPos);
	}

	private void showAddtoStack(final MGPointF targetpos) {
		if (delayForShow != null) {
			MGCallback executeFunc = MGCallback.action(new ActionCallback() {

				@Override
				public void execute(Object object) {

					MGDialog.this.AddToStack(targetpos);
				}
			});
			MGSequence sequence = MGSequence.actions(delayForShow,
					executeFunc);
			this.runAction(sequence);
		} else {
			AddToStack(targetpos);
		}

	}

	/** thời gian chờ xuất hiện dialog này, delay giây? **/
	public void setDelay(float delay) {
		if (delay > 0) {
			delayForShow = MGDelayTime.action(delay);
		}
		this.delay = delay;
	}

	/** khong goi ham nay neu chua hieu ro MGDialog **/
	public void AddToStack(MGPointF targetpos) {
		posToEnd.set(getPosition());
		if (MGDialogManager.shareManager().getAvailableDialog() == null
				|| MGDialogManager.shareManager().getAvailableDialog()
						.getAction(tag_actionComming) == null
				|| MGDialogManager.shareManager().getAvailableDialog()
						.getAction(tag_actionComming).isDone()) {
			// dieu kien tren la xet xem co dig log truoc hay chua, neu co
			// thi chay xong action show chua ,neu True thi moi cho them
			// dialog nay vao
			MGDialogManager.shareManager().pushDialog(this);
			MGIntervalAction action = easeActionForShow(showAction(targetpos));
			action.setTag(tag_actionComming);
			this.runAction(action);
			if (onShowListener != null) {
				onShowListener.onShow(this);
			}
		}
	}

	@Override
	public void dismiss() {
		runActionEnd();
		if (onDismissListener != null) {
			onDismissListener.onDismiss(this);
		}
	}

	@Override
	public void cancel() {
		runActionEnd();
		if (onCancelListener != null) {
			onCancelListener.onCancel(this);
		}

	}

	private void runActionEnd() {
		MGCallback callFunc = MGCallback.action(new ActionCallback() {

			@Override
			public void execute(Object object) {
				destroy();
			}
		});
		this.runAction(MGSequence.actions(
				easeActionForDismiss(dismissAction(posToEnd)), callFunc));
	}

	public final void destroy() {
		listButtons.clear();
		if (MGDialogManager.shareManager().dialogStack_.size() - 1 > 0) {
			MGDialogManager.shareManager().popDiaLog();
		} else {
			MGDialogManager.shareManager().clearAlldialog();
		}

		if (onDestroyListener != null) {
			onDestroyListener.onDesTroy(this);
		}

	}

	@Override
	public MGIntervalAction easeActionForShow(MGIntervalAction action) {
		return MGEaseSineOut.action(action);
	}

	@Override
	public MGIntervalAction showAction(MGPointF pointF) {

		this.setScaleX(0.0001f);
		this.setScaleX(0.7f);
		MGScaleTo scaleTo = MGScaleTo.action(0.3f, 1f, 1f);
		return MGSpawn.actions(scaleTo, MGMoveTo.action(0.2f, pointF));
	}

	@Override
	public MGIntervalAction dismissAction(MGPointF pointF) {

		MGScaleTo scaleTo = MGScaleTo.action(0.4f, 0.0001f, 0.5f);
		return MGSpawn.actions(scaleTo, MGMoveTo.action(0.3f, pointF));
	}

	@Override
	public MGIntervalAction easeActionForDismiss(MGIntervalAction action) {
		// TODO Auto-generated method stub
		return MGEaseBackIn.action(action);
	}

	@Override
	public MGIntervalAction hideAction() {
		return MGScaleTo.action(0.4f, 0.0001f);
	}

	@Override
	public MGIntervalAction popShowAction() {
		this.setScale(0.5f);
		MGScaleTo scaleTo = MGScaleTo.action(0.4f, 1f, 1f);
		return MGEaseExponentialOut.action(scaleTo);
	}

	public boolean onBackPressed() {
		if (canceledOnBackPressed) {
			dismiss();
		}
		return true;
	}

	/** hide = true ẩn dialog, hide = fale hiện dialog lại **/
	public final void setHideOfShowWithAnimate(final boolean hide) {
		if (hide) {
			MGCallback callback = MGCallback.action(new ActionCallback() {
				@Override
				public void execute(Object object) {
					setHide(hide);
				}
			});
			this.runAction(MGSequence.actions(hideAction(), callback));

		} else {
			setHide(hide);
			this.runAction(MGSequence.actions(popShowAction()));
		}

	}

	/** Cho phep nhan nut back de tat dialog hay khong **/
	public void setCanceledOnBackPressed(boolean canceledOnBackPressed) {
		this.canceledOnBackPressed = canceledOnBackPressed;
	}

	/** Cho phep touch ngoai dialog de tat dialog hay khong **/
	public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
		this.canceledOnTouchOutside = canceledOnTouchOutside;
	}

	/**
	 * Cho lop duoi dialog nay bat su kien touch khi onlyListenerEvent = false
	 * , mac dinh = true
	 **/
	public void setOnlyListenerEvent(boolean onlyListenerEvent) {
		this.onlyListenerEvent = onlyListenerEvent;
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public void setOnDismissListener(OnDismissListener onDismissListener) {
		this.onDismissListener = onDismissListener;
	}

	public void setOnCancelListener(OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	public void setOnShowListener(OnShowListener onShowListener) {
		this.onShowListener = onShowListener;
	}

	public void setOnDestroyListener(OnDestroyListener onDestroyListener) {
		this.onDestroyListener = onDestroyListener;
	}

	public final boolean onTouchBegan(PointF p) {
		onTouchDown(p);
		if (canceledOnTouchOutside && !contentRect.contains(p.x, p.y)) {
			if (closeButton != null ? !closeButton.onTouchBegan(convertPoint(p)) : true) {
				dismiss();
				return onlyListenerEvent;
			}
		}
		p.set(convertPoint(p));
		for (int i = 0; i < listButtons.size(); i++) {
			listButtons.get(i).onTouchBegan(p);
		}

		return onlyListenerEvent;
	}

	int tempID = MGButton.ivalid_value;

	public final boolean onTouchEnd(PointF p) {
		onTouchUp(p);
		// click on button
		p.set(convertPoint(p));
		for (int i = 0; i < listButtons.size(); i++) {
			tempID = listButtons.get(i).onTouchEnd(p);
			if (tempID != MGButton.ivalid_value) {
				if (onClickListener != null) {
					// lang nghe su kien va tra ve 1 id
					onClickListener.click(this, p, tempID);
				}
				
			}

			tempID = MGButton.ivalid_value;
		}
		return onlyListenerEvent;
	}

	public final boolean onTouchMove(float p1, float p2, float p3, float p4) {
		onTouchDrag(p1, p2, p3, p4);
		return onlyListenerEvent;
	}

	// cac ham de @Override lai khi can thiet

	public void onTouchDown(PointF p) {
	}

	public void onTouchUp(PointF p) {

	}

	public void onTouchDrag(float p1, float p2, float p3, float p4) {

	}

	@Override
	public MGNode addChild(MGNode child) {

		return super.addChild(child, 0);
	}

}
