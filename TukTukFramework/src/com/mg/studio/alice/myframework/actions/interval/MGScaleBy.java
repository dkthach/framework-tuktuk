package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.director.MGNode;


/** 
 * Scales a MGNode object a zoom factor by modifying it's scale attribute.
*/
public class MGScaleBy extends MGScaleTo {

    public static MGScaleBy action(float t, float s) {
        return new MGScaleBy(t, s, s);
    }

    public static MGScaleBy action(float t, float sx, float sy) {
        return new MGScaleBy(t, sx, sy);
    }

    protected MGScaleBy(float t, float s) {
        super(t, s, s);
    }

    protected MGScaleBy(float t, float sx, float sy) {
        super(t, sx, sy);
    }


    @Override
    public MGScaleBy copy() {
        return new MGScaleBy(duration, endScaleX, endScaleY);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        deltaX = startScaleX * endScaleX - startScaleX;
        deltaY = startScaleY * endScaleY - startScaleY;
    }

    @Override
    public MGScaleBy reverse() {
        return new MGScaleBy(duration, 1 / endScaleX, 1 / endScaleY);
    }
}

