/**
 * 
 */
package com.mg.studio.tuktuk.demotest;

import com.mg.studio.engine.MGImage;
import com.mg.studio.tuktuk.director.CanvasGame;
import com.mg.studio.tuktuk.resourcemanager.ResourceLoader;

/**
 * @author Dk Thach
 *
 */
public class RM {
	// defaul game size
	public static final float WIDTH_GAME = 640f;
	public static final float HEIGHT_GAME = 960f;
	public static float rate;
	public static MGImage ballBlue;

	public static void caculatorRatio() {
		rate = CanvasGame.widthDevices / WIDTH_GAME;
		rate = roundRate(rate);
	}

	public static float roundRate(float rate) {
		return (float) (Math.floor(rate * 20) / 20);
	}

	public static void loadrs() {
		caculatorRatio();
		ballBlue = ResourceLoader.load("circle.png", rate);
	}
}
