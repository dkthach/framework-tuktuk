/**
 * 
 */
package com.mg.studio.alice.myframework.actions;

import com.mg.studio.alice.myframework.director.MGNode;

/**
 * @author Dk Thach
 *
 */
public abstract class ABSProgressTimer extends MGNode {
	/** Percentages are from 0 to 100 */
	protected float percentage;
	public static final int BAR_PROGRESS_LEFT_TO_RIGHT = 1;
	public static final int BAR_PROGRESS_RIGHT_TO_LEFT = 2; 
	protected int type = BAR_PROGRESS_LEFT_TO_RIGHT;

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		if (this.percentage != percentage) {
			
			if (this.percentage < 0.f) {
				this.percentage = 0.f;
			} else if (percentage > 100.0f) {
				this.percentage = 100.f;
			} else {
				this.percentage = percentage;
			}
			updateProgress();
		}
	}
	public void setType(int type) {
		this.type = type;
	}

	public abstract void updateProgress();
	public abstract void progressTo(float toPercentage);

	

}
