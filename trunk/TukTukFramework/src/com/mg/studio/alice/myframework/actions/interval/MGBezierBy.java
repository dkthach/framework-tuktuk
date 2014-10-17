package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.type.MGBezierConfig;
import com.mg.studio.alice.myframework.type.MGPointF;



/** 
 * An action that moves the target with a cubic Bezier curve by a certain distance.
 */
public class MGBezierBy extends MGIntervalAction {

    protected MGBezierConfig config;
    protected MGPointF startPosition;

    /** creates the action with a duration and a bezier configuration */
    public static MGBezierBy action(float t, MGBezierConfig c) {
        return new MGBezierBy(t, c);
    }

    /** initializes the action with a duration and a bezier configuration */
    protected MGBezierBy(float t, MGBezierConfig c) {
        super(t);
        config = c;
        startPosition = MGPointF.make(0, 0);
    }

    @Override
    public MGIntervalAction copy() {
        return new MGBezierBy(duration, config);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        startPosition = target.getPosition();
    }

    @Override
    public void update(float t) {
        float xa = 0;
        float xb = config.controlPoint_1.x;
        float xc = config.controlPoint_2.x;
        float xd = config.endPosition.x;

        float ya = 0;
        float yb = config.controlPoint_1.y;
        float yc = config.controlPoint_2.y;
        float yd = config.endPosition.y;

        float x = MGBezierConfig.bezierAt(xa, xb, xc, xd, t);
        float y = MGBezierConfig.bezierAt(ya, yb, yc, yd, t);
        target.setPosition(MGPointF.make(startPosition.x + x, startPosition.y + y));
    }

    @Override
    public MGBezierBy reverse() {
        // TODO: reverse it's not working as expected
        MGBezierConfig r = new MGBezierConfig();
        r.endPosition = MGPointF.ccpNeg(config.endPosition);
        r.controlPoint_1 = MGPointF.ccpAdd(config.controlPoint_2, MGPointF.ccpNeg(config.endPosition));
        r.controlPoint_2 = MGPointF.ccpAdd(config.controlPoint_1, MGPointF.ccpNeg(config.endPosition));

        return new MGBezierBy(duration, r);
    }

}

