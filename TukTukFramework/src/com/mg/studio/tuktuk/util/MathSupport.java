package com.mg.studio.tuktuk.util;

import android.graphics.PointF;

/**
 * @author Dk Thach
 *
 */
public class MathSupport {
	
	/**
	 * Tra ve goc giua duong thang d(p0,p1) so voi truc X gia tri se trong
	 * khoang [ -Pi, Pi ].
	 */
	public static float angle(PointF p0, PointF p1) {
		float dx = p1.x - p0.x;
		float dy = p1.y - p0.y;
		return atan2(dy, dx);
	}
	public static float angle(float x1,float y1, float x2, float y2) {
		float dx = x2 - x1;
		float dy = y2 - y1;
		return atan2(dy, dx);
	}
	public static float distance(PointF p0, PointF p1) {
		float dx = p1.x - p0.x;
		float dy = p1.y - p0.y;
		return (float) Math.sqrt((dx*dx)+(dy*dy));
	}
	

	/**
	 * Tra ve gia tri se la so do goc nho nhat dc tao boi 2 vector
	 * v1(tail-tip1),v2(tail-tip2) gia tri se nam trong khoang [0-PI]
	 */
	public static float angleBetween(PointF tip1, PointF tail, PointF tip2) {
		float a1 = angle(tail, tip1);
		float a2 = angle(tail, tip2);

		return diff(a1, a2);
	}

	/**
	 * tra ve goc radian trong pham vi tu [0-PI]
	 */
	public static float diff(float ang1, float ang2) {
		float delAngle;

		if (ang1 < ang2) {
			delAngle = ang2 - ang1;
		} else {
			delAngle = ang1 - ang2;
		}

		if (delAngle > Math.PI) {
			delAngle = (float) ((2 * Math.PI) - delAngle);
		}

		return delAngle;
	}

	public static int getLenOfNum(int number) {
		int num = Math.abs(number);
		int len = 1;
		int n10 = 1;
		while (num >= n10 * 10) {
			len++;
			n10 = n10 * 10;
		}
		return len;
	}

	public static int aMb(int a, int b) {
		if (b == 0)
			return 1;
		return a * aMb(a, b - 1);
	}

	/**
	 * The <code>double</code> value that is closer than any other to <i>pi</i>,
	 * the ratio of the circumference of a circle to its diameter.
	 */
	public static final float PI = 3.14159265358979323846f;

	/**
	 * Returns the trigonometric cosine of an angle. Special cases:
	 * <ul>
	 * <li>If the argument is NaN or an infinity, then the result is NaN.
	 * </ul>
	 * <p>
	 * A result must be within 1 ulp of the correctly rounded result. Results
	 * must be semi-monotonic.
	 * 
	 * @param a
	 *            an angle, in radians.
	 * @return the cosine of the argument.
	 */
	public static float cos(float a) {
		return (float) Math.cos(a);
	}

	/**
	 * Returns the trigonometric sine of an angle. Special cases:
	 * <ul>
	 * <li>If the argument is NaN or an infinity, then the result is NaN.
	 * <li>If the argument is zero, then the result is a zero with the same sign
	 * as the argument.
	 * </ul>
	 * <p>
	 * A result must be within 1 ulp of the correctly rounded result. Results
	 * must be semi-monotonic.
	 * 
	 * @param a
	 *            an angle, in radians.
	 * @return the sine of the argument.
	 */
	public static float sin(float a) {
		return (float) Math.sin(a);
	}

	/**
	 * Returns the trigonometric tangent of an angle. Special cases:
	 * <ul>
	 * <li>If the argument is NaN or an infinity, then the result is NaN.
	 * <li>If the argument is zero, then the result is a zero with the same sign
	 * as the argument.
	 * </ul>
	 * <p>
	 * A result must be within 1 ulp of the correctly rounded result. Results
	 * must be semi-monotonic.
	 * 
	 * @param a
	 *            an angle, in radians.
	 * @return the tangent of the argument.
	 */
	public static float tan(float a) {
		return (float) Math.tan(a);
	}

	/**
	 * Returns the closest <code>int</code> to the argument. The result is
	 * rounded to an integer by adding 1/2, taking the floor of the result, and
	 * casting the result to type <code>int</code>. In other words, the result
	 * is equal to the value of the expression:
	 * <p>
	 * 
	 * <pre>
	 * (int) Math.floor(a + 0.5f)
	 * </pre>
	 * <p>
	 * Special cases:
	 * <ul>
	 * <li>If the argument is NaN, the result is 0.
	 * <li>If the argument is negative infinity or any value less than or equal
	 * to the value of <code>Integer.MIN_VALUE</code>, the result is equal to
	 * the value of <code>Integer.MIN_VALUE</code>.
	 * <li>If the argument is positive infinity or any value greater than or
	 * equal to the value of <code>Integer.MAX_VALUE</code>, the result is equal
	 * to the value of <code>Integer.MAX_VALUE</code>.
	 * </ul>
	 * 
	 * @param a
	 *            a floating-point value to be rounded to an integer.
	 * @return the value of the argument rounded to the nearest <code>int</code>
	 *         value.
	 * @see java.lang.Integer#MAX_VALUE
	 * @see java.lang.Integer#MIN_VALUE
	 */
	public static int round(float a) {
		return (int) Math.floor(a + 0.5f);
	}

	/**
	 * Returns the correctly rounded positive square root of a
	 * <code>float</code> value. Special cases:
	 * <ul>
	 * <li>If the argument is NaN or less than zero, then the result is NaN.
	 * <li>If the argument is positive infinity, then the result is positive
	 * infinity.
	 * <li>If the argument is positive zero or negative zero, then the result is
	 * the same as the argument.
	 * </ul>
	 * Otherwise, the result is the <code>float</code> value closest to the true
	 * mathematical square root of the argument value.
	 * 
	 * @param a
	 *            a value. <!--@return the value of &radic;&nbsp;<code>a</code>
	 *            .-->
	 * @return the positive square root of <code>a</code>. If the argument is
	 *         NaN or less than zero, the result is NaN.
	 */
	public static float sqrt(float a) {
		return (float) Math.sqrt(a);
	}

	/**
	 * Converts rectangular coordinates (<code>x</code>,&nbsp;<code>y</code>) to
	 * polar (r,&nbsp;<i>theta</i>). This method computes the phase <i>theta</i>
	 * by computing an arc tangent of <code>y/x</code> in the range of
	 * -<i>pi</i> to <i>pi</i>. Special cases:
	 * <ul>
	 * <li>If either argument is NaN, then the result is NaN.
	 * <li>If the first argument is positive zero and the second argument is
	 * positive, or the first argument is positive and finite and the second
	 * argument is positive infinity, then the result is positive zero.
	 * <li>If the first argument is negative zero and the second argument is
	 * positive, or the first argument is negative and finite and the second
	 * argument is positive infinity, then the result is negative zero.
	 * <li>If the first argument is positive zero and the second argument is
	 * negative, or the first argument is positive and finite and the second
	 * argument is negative infinity, then the result is the <code>float</code>
	 * value closest to <i>pi</i>.
	 * <li>If the first argument is negative zero and the second argument is
	 * negative, or the first argument is negative and finite and the second
	 * argument is negative infinity, then the result is the <code>float</code>
	 * value closest to -<i>pi</i>.
	 * <li>If the first argument is positive and the second argument is positive
	 * zero or negative zero, or the first argument is positive infinity and the
	 * second argument is finite, then the result is the <code>float</code>
	 * value closest to <i>pi</i>/2.
	 * <li>If the first argument is negative and the second argument is positive
	 * zero or negative zero, or the first argument is negative infinity and the
	 * second argument is finite, then the result is the <code>float</code>
	 * value closest to -<i>pi</i>/2.
	 * <li>If both arguments are positive infinity, then the result is the
	 * <code>float</code> value closest to <i>pi</i>/4.
	 * <li>If the first argument is positive infinity and the second argument is
	 * negative infinity, then the result is the <code>float</code> value
	 * closest to 3*<i>pi</i>/4.
	 * <li>If the first argument is negative infinity and the second argument is
	 * positive infinity, then the result is the <code>float</code> value
	 * closest to -<i>pi</i>/4.
	 * <li>If both arguments are negative infinity, then the result is the
	 * <code>float</code> value closest to -3*<i>pi</i>/4.
	 * </ul>
	 * <p>
	 * A result must be within 2 ulps of the correctly rounded result. Results
	 * must be semi-monotonic.
	 * 
	 * @param y
	 *            the ordinate coordinate
	 * @param x
	 *            the abscissa coordinate
	 * @return the <i>theta</i> component of the point
	 *         (<i>r</i>,&nbsp;<i>theta</i>) in polar coordinates that
	 *         corresponds to the point (<i>x</i>,&nbsp;<i>y</i>) in Cartesian
	 *         coordinates.
	 */
	public static float atan2(float y, float x) {
		// if x=y=0
		if ((y == 0.0f) && (x == 0.0f)) {
			return 0.0f;
		}

		// if x>0 atan(y/x)
		if (x > 0.0f) {
			return atan(y / x);
		}

		// if x<0 sign(y)*(pi - atan(|y/x|))
		if (x < 0.0f) {
			if (y < 0.0f) {
				return (float) (-(Math.PI - atan(y / x)));
			} else {
				return (float) (Math.PI - atan(-y / x));
			}
		}

		// if x=0 y!=0 sign(y)*pi/2
		if (y < 0.0f) {
			return (float) (-(Math.PI / 2.0f));
		} else {
			return (float) (Math.PI / 2.0f);
		}

	}

	/**
	 * Returns the arc tangent of an angle, in the range of -<i>pi</i>/2 through
	 * <i>pi</i>/2. Special cases:
	 * <ul>
	 * <li>If the argument is NaN, then the result is NaN.
	 * <li>If the argument is zero, then the result is a zero with the same sign
	 * as the argument.
	 * </ul>
	 * <p>
	 * A result must be within 1 ulp of the correctly rounded result. Results
	 * must be semi-monotonic.
	 * 
	 * @param a
	 *            the value whose arc tangent is to be returned.
	 * @return the arc tangent of the argument.
	 */
	public static float atan(float a) {
		// atan(x) = x/(1+ 0.28*x^2) (|x|<=1)
		// atan(x) = pi/2 - x/(x^2 + 0.28) (|x| >=1)

		if (abs(a) <= 1.0f) {
			return (a / (1 + 0.28f * (a * a)));
		} else {
			float retval = (((float) Math.PI) / 2.0f) - (a / ((a * a) + 0.28f));
			if (a < (-1.0f)) {
				return (retval - (float) Math.PI);
			} else {
				// if a > 1.0f
				return retval;
			}
		}
	}

	/**
	 * Returns the absolute value of an <code>int</code> value. If the argument
	 * is not negative, the argument is returned. If the argument is negative,
	 * the negation of the argument is returned.
	 * <p>
	 * Note that if the argument is equal to the value of
	 * <code>Integer.MIN_VALUE</code>, the most negative representable
	 * <code>int</code> value, the result is that same value, which is negative.
	 * 
	 * @param a
	 *            the argument whose absolute value is to be determined
	 * @return the absolute value of the argument.
	 * @see java.lang.Integer#MIN_VALUE
	 */
	public static int abs(int a) {
		return (a < 0) ? -a : a;
	}

	/**
	 * Returns the absolute value of a <code>float</code> value. If the argument
	 * is not negative, the argument is returned. If the argument is negative,
	 * the negation of the argument is returned. Special cases:
	 * <ul>
	 * <li>If the argument is positive zero or negative zero, the result is
	 * positive zero.
	 * <li>If the argument is infinite, the result is positive infinity.
	 * <li>If the argument is NaN, the result is NaN.
	 * </ul>
	 * In other words, the result is the same as the value of the expression:
	 * <p>
	 * 
	 * <pre>
	 * Float.intBitsToFloat(0x7fffffff &amp; Float.floatToIntBits(a))
	 * </pre>
	 * 
	 * @param a
	 *            the argument whose absolute value is to be determined
	 * @return the absolute value of the argument.
	 */
	public static float abs(float a) {
		return (a <= 0.0F) ? 0.0F - a : a;
	}

	/**
	 * Converts an angle measured in degrees to an approximately equivalent
	 * angle measured in radians. The conversion from degrees to radians is
	 * generally inexact.
	 * 
	 * @param angdeg
	 *            an angle, in degrees
	 * @return the measurement of the angle <code>angdeg</code> in radians.
	 * @since 1.2
	 */
	public static float toRadians(float angdeg) {
		return angdeg / 180.0f * PI;
	}

	/**
	 * Converts an angle measured in radians to an approximately equivalent
	 * angle measured in degrees. The conversion from radians to degrees is
	 * generally inexact; users should <i>not</i> expect
	 * <code>cos(toRadians(90.0))</code> to exactly equal <code>0.0</code>.
	 * 
	 * @param angrad
	 *            an angle, in radians
	 * @return the measurement of the angle <code>angrad</code> in degrees.
	 * @since 1.2
	 */
	public static float toDegrees(float angrad) {
		return angrad * 180.0f / PI;
	}

}
