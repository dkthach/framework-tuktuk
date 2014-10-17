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
	

	public OneMapImageNode(MGImage image) {
		
		this.image = image;
		setContentSize(image.getWidthContent(), image.getHeightContent());
	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.drawImage(image, 0, 0);
		
		
		
	}

}
