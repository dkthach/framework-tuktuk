/**
 * 
 */
package com.mg.studio.tuktuk.layers;

import android.opengl.GLES20;

import com.mg.studio.engine.MGColor;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.tuktuk.director.CanvasGame;
import com.mg.studio.tuktuk.events.MGRGBAProtocol;

/**
 * @author Dk Thach
 * 
 */
public class MGColorLayer extends MGLayer implements MGRGBAProtocol {
	/** size của thiết bị **/
	public static MGColorLayer node(MGColor color) {
		return new MGColorLayer(color);
	}

	/** custom size ***/
	public static MGColorLayer node(MGColor color, float w, float h) {
		return new MGColorLayer(color, w, h);
	}

	boolean newBlend;
	protected float opacity_;
	protected MGColor color;

	protected MGColorLayer(MGColor color) {
		init(color, CanvasGame.widthDevices, CanvasGame.heightDevices);
	}

	protected MGColorLayer(MGColor color, float w, float h) {
		init(color, w, h);
	}

	protected void init(MGColor color, float w, float h) {
		setContentSize(w, h);
		this.color = new MGColor(color.getAlpha(), color.getRed(),
				color.getGreen(), color.getBlue());
		opacity_ = color.getAlpha();
	}

	/*** Đừng xóa Super khi xài MGColorLayer ***/
	@Override
	public void drawRearChild(MGGraphic g) {
		newBlend = false;
		g.setColor(getColor());
	
		if (opacity_ != 1) {
			newBlend = true;
			 GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,
					GLES20.GL_ONE_MINUS_SRC_ALPHA);
		}
		g.drawRect(0, 0, contentSize_.width, contentSize_.height, true);
		if (newBlend) {
			g.enableBlendFunc();
		}
	

	}

	@Override
	public void setColor(MGColor color) {
		color.set(color);

	}

	@Override
	public MGColor getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public float getOpacity() {
		return opacity_;
	}

	/** opacity tu 0-1 ***/
	@Override
	public void setOpacity(float opacity) {
		this.opacity_ = opacity;
		color.setAlpha(opacity);
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
