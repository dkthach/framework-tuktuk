/**
 * 
 */
package com.mg.studio.alice.myframework.layers;

import android.graphics.PointF;

import com.mg.studio.alice.CanvasGame;
import com.mg.studio.alice.myframework.actions.interval.MGMoveTo;
import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.events.MGRGBAProtocol;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.alice.myframework.type.MGSize;
import com.mg.studio.engine.MGColor;
import com.mg.studio.engine.MGGraphic;

/**
 * @author Dk Thach
 * 
 */
public class CustomLayerForTransition extends MGLayer implements MGRGBAProtocol{
	public static CustomLayerForTransition node(float duration) {
		return new CustomLayerForTransition(duration);
	}

	// ArrayList<OnePiece> pieces;
	MGColor arrColor[];
	float duration;
	OnePiece onePiece01;
	

	protected CustomLayerForTransition(float d) {
		super.node();
		duration = d;
	}

	private void init() {
		// pieces = new ArrayList<OnePiece>();
		arrColor = new MGColor[4];
		arrColor[0] = new MGColor(1f, 1f, 0, 0);
		arrColor[1] = new MGColor(1f, 0, 1f, 0);
		arrColor[2] = new MGColor(1f, 0, 0, 1f);
		arrColor[2] = new MGColor(1f, 0, 1f, 0);
		MGSize s = CanvasGame.sizeDevices;
		onePiece01 = new OnePiece(arrColor[0], s.width / 2,
				s.height / 2);
		onePiece01.setPosition(s.width / 2, s.height / 2);
		
		OnePiece onePiece0 = new OnePiece(arrColor[0], s.width / 2,
				s.height / 2);
		onePiece0.setPosition(-s.width / 2, -s.height / 2);

		OnePiece onePiece1 = new OnePiece(arrColor[1], s.width / 2,
				s.height / 2);
		onePiece1.setPosition(s.width, -s.height / 2);
		OnePiece onePiece2 = new OnePiece(arrColor[2], s.width / 2,
				s.height / 2);
		onePiece2.setPosition(s.width, s.height);
		OnePiece onePiece3 = new OnePiece(arrColor[3], s.width / 2,
				s.height / 2);
		onePiece2.setPosition(-s.width / 2, s.height);
		addChild(onePiece0);
		addChild(onePiece1);
		addChild(onePiece2);
		addChild(onePiece3);
		MGMoveTo a0 = MGMoveTo.action(duration / 2, new MGPointF(0, 0));
		MGMoveTo a1 = MGMoveTo.action(duration / 2, new MGPointF(s.width / 2,
				0));
		MGMoveTo a2 = MGMoveTo.action(duration / 2, new MGPointF(s.width / 2,
				s.height / 2));
		MGMoveTo a3 = MGMoveTo.action(duration / 2, new MGPointF(0,
				s.height / 2));
		onePiece0.runAction(a0);
		onePiece1.runAction(a1);
		onePiece2.runAction(a2);
		onePiece3.runAction(a3);

	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.drawRect(position_.x, position_.y,200,
				200, true);
		onePiece01.drawRearChild(g);
		
		
	}

	

	// 1 mảnh hình chu nhat
	public class OnePiece extends MGNode  {
		MGColor color;
		float opacity;

		public OnePiece(MGColor color, float w, float h) {
			super.node();
			setContentSize(w, h);
			this.opacity = color.getAlpha();
			this.color.set(color);

		}

		@Override
		public void drawRearChild(MGGraphic g) {
			// g.enableBlendFunc();
			g.setColor(1f, 1, 0, 0);
			g.drawRect(position_.x, position_.y, contentSize_.width,
					contentSize_.height, true);
			g.drawCircle(new PointF(200, 200), 150);
		}

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
		return 0;
	}

	@Override
	public void setOpacity(float opacity) {
		// TODO Auto-generated method stub
		
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
