/**
 * 
 */
package com.mg.studio.tuktuk.director;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.engine.MGMaskObject;
import com.mg.studio.tuktuk.actions.ABSProgressTimer;
import com.mg.studio.tuktuk.actions.ease.MGEaseExponentialOut;
import com.mg.studio.tuktuk.actions.interval.MGProgressTo;
import com.mg.studio.tuktuk.type.MGPointF;

/**
 * @author Dk Thach
 *
 */
public class ProgressBar extends ABSProgressTimer {
	MGImage bgBarImage;
	MGImage colorBarImage;
	MGMaskObject mgMaskObject;
	float leftMask, topMask, widthMask, heightMask;
	float realDuration = 1f;
	private MGPointF currentTailPosition;

	public ProgressBar(MGImage bgBarImage, MGImage colorBarImage) {
		setBgBarImage(bgBarImage);
		setColorBarImage(colorBarImage);
		init();
	}

	public void setBgBarImage(MGImage bgBarImage) {
		this.bgBarImage = bgBarImage;
	}

	public void setColorBarImage(MGImage colorBarImage) {
		this.colorBarImage = colorBarImage;
	}

	private void init() {
		setContentSize(colorBarImage.getWidthContent(),
				colorBarImage.getHeightContent());
		resetToZeroPercentage();
		heightMask = contentSize_.height;
		mgMaskObject = new MGMaskObject() {
			public void paintMask(MGGraphic g) {
				g.drawRect(leftMask, topMask, widthMask, heightMask, true);
			}
		};

	}

	/** thoi gian hoan thanh action progressTo **/
	public void setRealDuration(float realDuration) {
		this.realDuration = realDuration;
	}

	public void resetToZeroPercentage() {
		setPercentage(0);
		stopAllActions();
	}

	/**
	 * @return Toa do diem cuoi hien tai cua progress bar
	 */
	public MGPointF getCurrentTailPosition() {
		if (currentTailPosition == null) {
			currentTailPosition = MGPointF.zero();
			updateProgress();
		}
		return currentTailPosition;
	}

	@Override
	/**chay animation toi Percentage **/
	public void progressTo(float toPercentage) {
		this.runAction(MGEaseExponentialOut.action(MGProgressTo.action(
				realDuration, toPercentage)));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.mg.studio.alice.myframework.actions.ABSProgressTimer#updateProgress
	 * ()
	 */
	@Override
	public void updateProgress() {
		switch (type) {
		case BAR_PROGRESS_LEFT_TO_RIGHT:
		leftMask = 0;
		topMask = 0;
		widthMask = contentSize_.width * percentage / 100;
		if (currentTailPosition != null) {
			currentTailPosition.set(widthMask, topMask);
		}
			break;
		case BAR_PROGRESS_RIGHT_TO_LEFT:
		widthMask = contentSize_.width * percentage / 100;
		leftMask = contentSize_.width - widthMask;
		topMask = 0;
		if (currentTailPosition != null) {
			currentTailPosition.set(leftMask, topMask);
		}

			break;

		default:
			break;
		}

	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.drawImage(bgBarImage, 0, 0);
		g.beginMask(g, mgMaskObject, MGGraphic.MASK_STYLE_INSIDE);
		g.drawImage(colorBarImage, 0, 0);
		g.endMask();
	}

}
