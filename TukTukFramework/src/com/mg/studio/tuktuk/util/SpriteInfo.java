/**
 * 
 */
package com.mg.studio.tuktuk.util;

import android.graphics.RectF;

/**
 * @author Dk Thach
 * 
 */
public class SpriteInfo {
	public String name;
	public RectF rectcutframe;
	public SpriteInfo() {
		rectcutframe = new RectF();
	}

	@Override
	public String toString() {
		return "n= " + name;
	}

}
