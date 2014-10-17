package com.mg.studio.tuktuk.director;

import android.graphics.PointF;

import com.mg.studio.engine.MGFont;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.bas.MGAction;
import com.mg.studio.tuktuk.actions.bas.MGRepeatForever;
import com.mg.studio.tuktuk.actions.ease.MGEaseElasticOut;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.actions.interval.MGScaleInAndOut;
import com.mg.studio.tuktuk.actions.interval.MGScaleTo;
import com.mg.studio.tuktuk.actions.interval.MGSequence;

public class MGButton extends MGNode {
	public static int ivalid_value = 0xdefee;
	public static int K_FUNY_ACTION = 0XF12;
	public static int K_SELECT_ACTION = 0XF13;
	private MGImage image;
	private boolean isSelect, isOnFunnyAction;
	private MGRepeatForever repeatForever = null;
	private String text = null;
	private MGFont font = null;

	public MGButton(MGImage image) {
		super();
		setImage(image);
		// defaul anchor pos X: CENTER Y: CENTER
		setAnchorPoint(0.5f, 0.5f);

	}

	public MGButton() {
		super();
		// defaul anchor pos X: CENTER Y: CENTER
		setAnchorPoint(0.5f, 0.5f);
	}

	public void setImage(MGImage image) {
		this.image = image;
		setContentSize(image.getWidthContent(), image.getHeightContent());
	}

	public void setId(int id) {
		setTag(id);
	}

	public int getId() {
		return getTag();
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFont(MGFont font) {
		this.font = font;
	}

	@Override
	public void drawRearChild(MGGraphic g) {
		if (image != null) {
			g.drawImage(image, 0, 0);
		}
		if (text != null) {
			g.setColor(1f, 1f, 1f, 1f);
			g.setFont(font);
			g.drawText(text, (int) (contentSize_.width / 2),
					(int) (contentSize_.height / 2),
					MGFont.ALIGN_CENTER_HORIZONTAL
							| MGFont.ALIGN_CENTER_VERTICAL);
		}
	}

	public boolean onTouchBegan(PointF p) {
		if (contentRect.contains(p.x, p.y)) {
			stopFunnyAction();
			isSelect = true;
			MGAction select = selectAction();
			runAction(select);
			return true;
		}
		return false;
	}

	public int onTouchEnd(PointF p) {
		if (!isSelect) {
			return ivalid_value;
		}
		isSelect = false;
		this.runAction(MGSequence.actions(unSeclectAction(),
				MGCallback.action(new ActionCallback() {
					@Override
					public void execute(Object object) {
						if (isOnFunnyAction) {
							runFunnyAction(true);
						}

					}

				})));

		if (contentRect.contains(p.x, p.y)) {

			return getId();
		}
		return ivalid_value;
	}

	public void onTouchDrag(float a, float b, float c) {

	}

	public MGIntervalAction selectAction() {
		return MGEaseElasticOut.action(MGScaleTo.action(0.2f, 0.84f));
	}

	public MGIntervalAction unSeclectAction() {
		return MGEaseElasticOut.action(MGScaleTo.action(0.3f, 1f));
	}

	public MGIntervalAction funnyAction() {
		return MGScaleInAndOut.action(2f, 0.95f, 1.05f, 0.96f, 1.02f);
	}

	public final void runFunnyAction(boolean isOn) {
		isOnFunnyAction = isOn;
		if (isOnFunnyAction && repeatForever == null) {
			repeatForever = MGRepeatForever.action(funnyAction());
			runAction(repeatForever);
		}

	}

	public void stopFunnyAction() {
		if (repeatForever != null) {
			this.stopAction(repeatForever);
			repeatForever = null;
		}
	}

}
