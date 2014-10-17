/**
 * 
 */
package com.mg.studio.tuktuk.scrollview;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.tuktuk.director.MGNode;

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
