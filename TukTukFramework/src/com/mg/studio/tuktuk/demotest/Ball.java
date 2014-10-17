/**
 * 
 */
package com.mg.studio.tuktuk.demotest;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.tuktuk.director.MGNode;

public class Ball extends MGNode {

	final MGImage image;

	public Ball(MGImage image) {
		this.image = image;
		setContentSize(image.getWidthContent(), image.getHeightContent());
	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.drawImage(image, 0, 0);
	}

}
