/**
 * 
 */
package com.mg.studio.alice.myframework.scrollview;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;

/**
 * @author Dk Thach
 *
 */
public class OneMapImageNode extends MGNode {

	private final MGImage image;
	String text;

	public OneMapImageNode(MGImage image, String text) {
		this.text = text;
		this.image = image;
		setContentSize(image.getWidthContent(), image.getHeightContent());
	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.drawImage(image, 0, 0);
		g.setColor(1f, 1f, 1f, 1f);
		g.setFont(RM.font_sniglet_46_Blue_export);
		g.drawText(text, 0, 0);
	}

}
