package com.mg.studio.alice.myframework.util;

import android.graphics.RectF;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;

public class NumberFont {
    public static int ANCHOR_LEFT = 0;
    public static int ANCHOR_CENTER = 1;
    public static int ANCHOR_RIGHT = 2;
    private MGImage imgNumber;
    public float numWidth, numHeight;
    
    private RectF rects[];

    public NumberFont(MGImage imgNumber) {
        this.imgNumber = imgNumber;
        numHeight = imgNumber.getHeightContent();
        numWidth = imgNumber.getWidthContent() / 10;
        rects = new RectF[10];
        for (int i = 0; i < rects.length; i++) {
            rects[i] = new RectF(i * numWidth, 0, i * numWidth + numWidth, imgNumber.getHeightContent());
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
            drawANumber(num - firstDigit * MathSupport.aMb(10, lenNum - 1), lenNum - 1, x + numWidth, y, g);
        }
    }
}
