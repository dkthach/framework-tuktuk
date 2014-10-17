package com.mg.studio.tuktuk.type;

/**
 * @author DK Thach
 *
 */
public class MGSize {

	public float width, height;

	private MGSize() {
		this(0, 0);
	}

	private MGSize(float w, float h) {
		width = w;
		height = h;
	}

	public static MGSize make(float w, float h) {
		return new MGSize(w, h);
	}

	public static MGSize zero() {
		return new MGSize(0, 0);
	}

	public void set(MGSize s) {
		width = s.width;
		height = s.height;
	}

	public void set(float w, float h) {
		width = w;
		height = h;
	}

	private static MGSize ZERO_SIZE = MGSize.zero();

	public static MGSize getZero() {
		return ZERO_SIZE;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public static boolean equalToSize(MGSize s1, MGSize s2) {
		return s1.width == s2.width && s1.height == s2.height;
	}

	public String toString() {
		return "<" + width + ", " + height + ">";
	}
}
