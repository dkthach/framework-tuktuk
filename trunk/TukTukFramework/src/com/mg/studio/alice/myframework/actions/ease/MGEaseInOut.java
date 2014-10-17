package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseInOut extends MGEaseRateAction {

    public static MGEaseInOut action(MGIntervalAction action, float rate) {
        return new MGEaseInOut(action, rate);
    }

    protected MGEaseInOut(MGIntervalAction action, float rate) {
        super(action, rate);
    }

    @Override
    public void update(float t) {
        int sign = 1;
        int r = (int) rate;
        if (r % 2 == 0)
            sign = -1;

        t *= 2;
        if (t < 1)
            other.update(0.5f * (float) Math.pow(t, rate));
        else
            other.update(sign * 0.5f * ((float) Math.pow(t - 2, rate) + sign * 2));
    }


    // InOut and OutIn are symmetrical
    @Override
	public MGIntervalAction reverse()  {
		return new MGEaseInOut(other.reverse(), rate);
	}

}
