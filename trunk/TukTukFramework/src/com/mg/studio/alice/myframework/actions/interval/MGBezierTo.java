package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.type.MGBezierConfig;
import com.mg.studio.alice.myframework.type.MGPointF;


/** An action that moves the target with a cubic Bezier curve to a destination point.
 */
public class MGBezierTo extends MGBezierBy {

    /** creates the action with a duration and a bezier configuration */
    public static MGBezierTo action(float t, MGBezierConfig c) {
        return new MGBezierTo(t, c);
    }

    /** initializes the action with a duration and a bezier configuration */
    protected MGBezierTo(float t, MGBezierConfig c) {
        super(t, c);
    }

    @Override
    public MGBezierTo copy() {
        return new MGBezierTo(duration, config);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);

        config.controlPoint_1 = MGPointF.ccpSub(config.controlPoint_1, startPosition);
        config.controlPoint_2 = MGPointF.ccpSub(config.controlPoint_2, startPosition);
        config.endPosition = MGPointF.ccpSub(config.endPosition, startPosition);
    }

    @Override
    public MGBezierTo reverse() {
        // TODO: reverse it's not working as expected
        MGBezierConfig r = new MGBezierConfig();
       r.endPosition = MGPointF.ccpNeg(config.endPosition);
        r.controlPoint_1 = MGPointF.ccpAdd(config.controlPoint_2, MGPointF.ccpNeg(config.endPosition));
        r.controlPoint_2 = MGPointF.ccpAdd(config.controlPoint_1, MGPointF.ccpNeg(config.endPosition));

        return new MGBezierTo(duration, r);
    }
}

