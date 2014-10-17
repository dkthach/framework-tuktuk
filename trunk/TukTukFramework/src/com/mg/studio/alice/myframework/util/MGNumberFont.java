package com.mg.studio.alice.myframework.util;

import android.graphics.RectF;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;

public class MGNumberFont {
	public static int ANCHOR_LEFT = 0;
	public static int ANCHOR_CENTER = 1;
	public static int ANCHOR_RIGHT = 2;
	private MGImage imgNumber;

	public float numWidth, numHeight;
private	float[] listSizeWidthFrameNumber;

	private RectF rects[];

	/**
	 * Dùng khởi tạo này khi sheetSprite số có 10 frame số ,frame width đều nhau
	 **/
	public MGNumberFont(MGImage imgNumber) {
		this.imgNumber = imgNumber;
		numHeight = imgNumber.getHeightContent();
		numWidth = imgNumber.getWidthContent() / 10;
		rects = new RectF[10];
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new RectF(i * numWidth, 0, i * numWidth + numWidth,
					imgNumber.getHeightContent());
		
		}
	}

	/**
	 * Dùng khởi tạo này khi sheetSprite có số lượng frame tùy ý, frame width có
	 * thể khác nhau tùy vào listSizeWidthFrameNumber[] truyền vào
	 **/
	public MGNumberFont(MGImage imgNumber, float[] listSizeWidthFrameNumber
			) {
		this.imgNumber = imgNumber;
		
		this.listSizeWidthFrameNumber = listSizeWidthFrameNumber;
		int  numberFrame = listSizeWidthFrameNumber.length;
		numHeight = imgNumber.getHeightContent();
		numWidth = imgNumber.getWidthContent() / numberFrame;
		rects = new RectF[numberFrame];
		float tembSize = 0;
		for (int i = 0; i < rects.length; i++) {

			rects[i] = new RectF(tembSize, 0, tembSize
					+ (listSizeWidthFrameNumber[i]*RM.rate), imgNumber.getHeightContent());
			tembSize += (listSizeWidthFrameNumber[i]*RM.rate);
		}
	}

	public void drawANumber(int num, float x, float y, MGGraphic g, int anchor) {
		int len = MathSupport.getLenOfNum(num);
		if (anchor == ANCHOR_LEFT)
			drawANumber(num, len, x, y, g);
		else if (anchor == ANCHOR_CENTER)
			drawANumber(num, len, x - (numWidth * len) / 2, y, g);
		else
			drawANumber(num, len, x - numWidth * len, y, g);
	}

	private void drawANumber(int num, int lenNum, float x, float y, MGGraphic g) {
		if (lenNum == 1)
			g.drawImage(imgNumber, x, y, rects[num]);
		else {
			int firstDigit = num / (MathSupport.aMb(10, lenNum - 1));
			g.drawImage(imgNumber, x, y, rects[firstDigit]);
			drawANumber(num - firstDigit * MathSupport.aMb(10, lenNum - 1),
					lenNum - 1, x + listSizeWidthFrameNumber[firstDigit]*RM.rate, y, g);
		}
	}
}
