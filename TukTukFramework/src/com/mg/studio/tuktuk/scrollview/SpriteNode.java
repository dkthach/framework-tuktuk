package com.mg.studio.tuktuk.scrollview;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.tuktuk.director.MGNode;

public class SpriteNode extends MGNode {
	private final MGImage image;

	public static SpriteNode create(MGImage image) {
		return new SpriteNode(image);

	}

	private SpriteNode(MGImage image) {
		this.image = image;
		setContentSize(image.getWidthContent(), image.getHeightContent());
	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.drawImage(image, 0, 0);
	}

}
