/**
 * 
 */
package com.mg.studio.alice.myframework.layers;

import com.mg.studio.alice.game.resoucredata.RM;
import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.events.MGRGBAProtocol;
import com.mg.studio.engine.MGColor;
import com.mg.studio.engine.MGGraphic;

/**
 * @author Dk Thach
 *
 */
public class TransEffectLayer extends MGNode implements MGRGBAProtocol {
	float opacity;

	public TransEffectLayer() {
		super();
		setAnchorPoint(0.5f, 0.5f);
		setContentSize(RM.effectTrans.getWidthContent(),
				RM.effectTrans.getHeightContent());

	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.enableBlendFunc();
		g.setImageTransparent(opacity);
		g.drawImage(RM.effectTrans, 0, 0);
		g.drawImage(RM.logo,
				contentSize_.width / 2 - RM.logo.getWidthContent() / 2,
				contentSize_.height / 2 - RM.logo.getHeightContent() / 2);
		g.setImageTransparent(1);
		g.disableBlendFunc();
	}

	@Override
	public void setColor(MGColor color) {
		// TODO Auto-generated method stub

	}

	@Override
	public MGColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getOpacity() {
		// TODO Auto-generated method stub
		return opacity;
	}

	@Override
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	@Override
	public void setOpacityModifyRGB(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean doesOpacityModifyRGB() {
		// TODO Auto-generated method stub
		return false;
	}

}
