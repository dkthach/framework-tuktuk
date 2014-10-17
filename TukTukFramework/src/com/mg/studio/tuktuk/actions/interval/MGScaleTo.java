package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.director.MGNode;


/** Scales a MGNode object to a zoom factor by modifying it's scale attribute.
 @warning This action doesn't support "reverse"
 */
public class MGScaleTo extends MGIntervalAction {
    protected float scaleX;
    protected float scaleY;
    protected float startScaleX;
    protected float startScaleY;
    protected float endScaleX;
    protected float endScaleY;
    protected float deltaX;
    protected float deltaY;

    /** creates the action with the same scale factor for X and Y */
    public static MGScaleTo action(float t, float s) {
        return new MGScaleTo(t, s);
    }

    /** creates the action with and X factor and a Y factor */
    public static MGScaleTo action(float t, float sx, float sy) {
        return new MGScaleTo(t, sx, sy);
    }

    /** initializes the action with the same scale factor for X and Y */
    protected MGScaleTo(float t, float s) {
        this(t, s, s);
    }

    /** initializes the action with and X factor and a Y factor */
    protected MGScaleTo(float t, float sx, float sy) {
        super(t);
        endScaleX = sx;
        endScaleY = sy;
    }

    @Override
    public MGScaleTo copy() {
        return new MGScaleTo(duration, endScaleX, endScaleY);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
      
        startScaleX = target.getScaleX();
        startScaleY = target.getScaleY();
        deltaX = endScaleX - startScaleX;
        deltaY = endScaleY - startScaleY;
    }
 
    @Override
    public void update(float t) {
        target.setScaleX(startScaleX + deltaX * t);
        target.setScaleY(startScaleY + deltaY * t);
    }
}

