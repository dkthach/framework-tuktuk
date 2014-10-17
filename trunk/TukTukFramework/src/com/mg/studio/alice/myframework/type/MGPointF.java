package com.mg.studio.alice.myframework.type;

import android.graphics.PointF;

import com.mg.studio.alice.myframework.config.MGMacros;


/**
 * @author Dk Thach
 *
 */
public class MGPointF extends PointF{
    private static final float kCGPointEpsilon = 0.00000012f; 
   
    
    private static final MGPointF ZERO_POINT = new MGPointF(0, 0);
    public static MGPointF getZero() {
    	return ZERO_POINT;
    }
    
    public static MGPointF zero() {
        return new MGPointF(0, 0);
    }

    public static MGPointF make(float x, float y) {
        return new MGPointF(x, y);
    }

    public MGPointF() {
        this(0, 0);
    }

    public MGPointF(float x, float y) {
       super(x, y);
    }
    
  
    
    public void set(MGPointF p) {
    	this.x = p.x;
    	this.y = p.y;
    }
    
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static boolean equalToPoint(MGPointF p1, MGPointF p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    public static MGPointF applyAffineTransform(MGPointF aPoint, MGAffineTransform aTransform) {
        return aTransform.applyTransform(aPoint);
    }

    /**
     * Helper macro that creates a MGPoint
     *
     * @return MGPoint
     */
    public static MGPointF ccp(float x, float y) {
        return new MGPointF(x, y);
    }


    /**
     * Returns opposite of point.
     *
     * @return MGPoint
     */
    public static MGPointF ccpNeg(final MGPointF v) {
        return ccp(-v.x, -v.y);
    }

    /**
     * Calculates sum of two points.
     *
     * @return MGPoint
     */
    public static MGPointF ccpAdd(final MGPointF v1, final MGPointF v2) {
        return ccp(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Calculates difference of two points.
     *
     * @return MGPoint
     */
    public static MGPointF ccpSub(final MGPointF v1, final MGPointF v2) {
        return ccp(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Returns point multiplied by given factor.
     *
     * @return MGPoint
     */
    public static MGPointF ccpMult(final MGPointF v, final float s) {
        return ccp(v.x * s, v.y * s);
    }

    /**
     * Calculates midpoint between two points.
     *
     * @return MGPoint
     */
    public static MGPointF ccpMidpoint(final MGPointF v1, final MGPointF v2) {
        return ccpMult(ccpAdd(v1, v2), 0.5f);
    }

    /**
     * Calculates dot product of two points.
     *
     * @return float
     */
    public static float ccpDot(final MGPointF v1, final MGPointF v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Calculates cross product of two points.
     *
     * @return float
     */
    public static float ccpCross(final MGPointF v1, final MGPointF v2) {
        return v1.x * v2.y - v1.y * v2.x;
    }

    /**
     * Calculates perpendicular of v, rotated 90 degrees counter-clockwise -- cross(v, perp(v)) >= 0
     *
     * @return MGPoint
     */
    public static MGPointF ccpPerp(final MGPointF v) {
        return ccp(-v.y, v.x);
    }

    /**
     * Calculates perpendicular of v, rotated 90 degrees clockwise -- cross(v, rperp(v)) <= 0
     *
     * @return MGPoint
     */
    public static MGPointF ccpRPerp(final MGPointF v) {
        return ccp(v.y, -v.x);
    }

    /**
     * Calculates the projection of v1 over v2.
     *
     * @return MGPoint
     */
    public static MGPointF ccpProject(final MGPointF v1, final MGPointF v2) {
        return ccpMult(v2, ccpDot(v1, v2) / ccpDot(v2, v2));
    }

    /**
     * Rotates two points.
     *
     * @return MGPoint
     */
    public static MGPointF ccpRotate(final MGPointF v1, final MGPointF v2) {
        return ccp(v1.x * v2.x - v1.y * v2.y, v1.x * v2.y + v1.y * v2.x);
    }

    /**
     * Unrotates two points.
     *
     * @return MGPoint
     */
    public static MGPointF ccpUnrotate(final MGPointF v1, final MGPointF v2) {
        return ccp(v1.x * v2.x + v1.y * v2.y, v1.y * v2.x - v1.x * v2.y);
    }

    /**
     * Calculates the square length of a MGPoint (not calling sqrt() )
     *
     * @return float
     */
    public static float ccpLengthSQ(final MGPointF v) {
        return ccpDot(v, v);
    }

    /** Calculates distance between point and origin
     @return CGFloat
     @since v0.7.2
     */
    public static float ccpLength(final MGPointF v) {
        return (float)Math.sqrt(ccpLengthSQ(v));
    }

    /**
     * Calculates the distance between two points
     *
     * @return float
     */
    public static float ccpDistance(final MGPointF v1, final MGPointF v2) {
        return ccpLength(ccpSub(v1, v2));
    }

    /**
     * Returns point multiplied to a length of 1.
     *
     * @return MGPoint
     */
    public static MGPointF ccpNormalize(final MGPointF v) {
        return ccpMult(v, 1.0f / ccpLength(v));
    }

    /**
     * Converts radians to a normalized vector.
     *
     * @return MGPoint
     */
    public static MGPointF ccpForAngle(final float a) {
        return ccp((float)Math.cos(a), (float)Math.sin(a));
    }

    /**
     * Converts a vector to radians.
     *
     * @return float
     */
    public static float ccpToAngle(final MGPointF v) {
        return (float) Math.atan2(v.y, v.x);
    }

    /**
     *  Caculate the rotation(in degrees) between two points,
     *      so that when we move from one point to the other,
     *      we can set the correct rotation to head to that point.
     *
     * @param from
     * @param to
     * @return the rotation in degrees
     */
    public static float ccpCalcRotate(final MGPointF from, final MGPointF to) {
        float o = to.x - from.x;
        float a = to.y - from.y;
        float at = MGMacros.CC_RADIANS_TO_DEGREES((float) Math.atan(o / a));

        if (a < 0) {
            if (o < 0)
                at = 180 + Math.abs(at);
            else
                at = 180 - Math.abs(at);
        }

        return at;
    }


    /** @returns the angle in radians between two vector directions
      @since v0.99.1
      */
    public static float ccpAngle(MGPointF a, MGPointF b) {
        float angle = (float)Math.acos(ccpDot(ccpNormalize(a), ccpNormalize(b)));
        if( Math.abs(angle) < kCGPointEpsilon ) return 0.f;
        return angle;
    }

    /** Linear Interpolation between two points a and b
     @returns
        alpha == 0 ? a
        alpha == 1 ? b
        otherwise a value between a..b
     @since v0.99.1
     */
    public static MGPointF ccpLerp(MGPointF a, MGPointF b, float alpha) {
        return ccpAdd(ccpMult(a, 1.f - alpha), ccpMult(b, alpha));
    }

    /** Clamp a value between from and to.
      @since v0.99.1
      */
    public static float clampf(float value, float min_inclusive, float max_inclusive) {
        if (min_inclusive > max_inclusive) {
            float tmp = min_inclusive;
            min_inclusive = max_inclusive;
            max_inclusive = tmp;
        }

        return value < min_inclusive ? min_inclusive : value < max_inclusive? value : max_inclusive;
    }

    /** Clamp a point between from and to.
      @since v0.99.1
      */
    public static MGPointF ccpClamp(MGPointF p, MGPointF min_inclusive, MGPointF max_inclusive) {
        return ccp(clampf(p.x,min_inclusive.x,max_inclusive.x), 
                    clampf(p.y, min_inclusive.y, max_inclusive.y));
    }

    /** Quickly convert MGSize to a MGPoint
      */
    public static MGPointF ccpFromSize(MGSize s) {
        return ccp(s.width, s.height);
    }

    /** @returns if points have fuzzy equality which means equal with some degree of variance.
      */
    public static boolean ccpFuzzyEqual(MGPointF a, MGPointF b, float var) {
        if(a.x - var <= b.x && b.x <= a.x + var)
            if(a.y - var <= b.y && b.y <= a.y + var)
                return true;
        return false;
    }

    /** Multiplies a nd b components, a.x*b.x, a.y*b.y
      @returns a component-wise multiplication
      @since v0.99.1
      */
    public static MGPointF ccpCompMult(MGPointF a, MGPointF b) {
        return ccp(a.x * b.x, a.y * b.y);
    }

    /** @returns the signed angle in radians between two vector directions
      @since v0.99.1
      */
    public static float ccpAngleSigned(MGPointF a, MGPointF b) {
        MGPointF a2 = ccpNormalize(a);
        MGPointF b2 = ccpNormalize(b);
        float angle = (float)Math.atan2(a2.x * b2.y - a2.y * b2.x, ccpDot(a2, b2));
        if( Math.abs(angle) < kCGPointEpsilon ) return 0.f;
        return angle;
    }

    /** Rotates a point counter clockwise by the angle around a pivot
      @param v is the point to rotate
      @param pivot is the pivot, naturally
      @param angle is the angle of rotation cw in radians
      @returns the rotated point
      @since v0.99.1
      */
    public static MGPointF ccpRotateByAngle(MGPointF v, MGPointF pivot, float angle) {
        MGPointF r = ccpSub(v, pivot);
        float t = r.x;
        float cosa = (float)Math.cos(angle);
        float sina = (float)Math.sin(angle);
        r.x = t*cosa - r.y*sina;
        r.y = t*sina + r.y*cosa;
        r = ccpAdd(r, pivot);
        return r;
    }

    /** A general line-line intersection test
      @param p1 
      is the startpoint for the first line P1 = (p1 - p2)
      @param p2 
      is the endpoint for the first line P1 = (p1 - p2)
      @param p3 
      is the startpoint for the second line P2 = (p3 - p4)
      @param p4 
      is the endpoint for the second line P2 = (p3 - p4)
      @param s 
      is the range for a hitpoint in P1 (pa = p1 + s*(p2 - p1))
      @param t
      is the range for a hitpoint in P3 (pa = p2 + t*(p4 - p3))
      @return bool 
      indicating successful intersection of a line
      note that to truly test intersection for segments we have to make 
      sure that s & t lie within [0..1] and for rays, make sure s & t > 0
      the hit point is		p3 + t * (p4 - p3);
      the hit point also is	p1 + s * (p2 - p1);
      @since v0.99.1
      */
    public static boolean ccpLineIntersect(MGPointF p1, MGPointF p2, 
            MGPointF p3, MGPointF p4, MGPointF ret){
        MGPointF p13, p43, p21;
        float d1343, d4321, d1321, d4343, d2121;
        float numer, denom;

        p13 = ccpSub(p1, p3);

        p43 = ccpSub(p4, p3);

        //Roughly equal to zero but with an epsilon deviation for float 
        //correction
        if (ccpFuzzyEqual(p43, MGPointF.zero(), kCGPointEpsilon))
            return false;

        p21 = ccpSub(p2, p1);

        //Roughly equal to zero
        if (ccpFuzzyEqual(p21,MGPointF.zero(), kCGPointEpsilon))
            return false;

        d1343 = ccpDot(p13, p43);
        d4321 = ccpDot(p43, p21);
        d1321 = ccpDot(p13, p21);
        d4343 = ccpDot(p43, p43);
        d2121 = ccpDot(p21, p21);

        denom = d2121 * d4343 - d4321 * d4321;
        if (Math.abs(denom) < kCGPointEpsilon)
            return false;
        numer = d1343 * d4321 - d1321 * d4343;

        ret.x = numer / denom;
        ret.y = (d1343 + d4321 * ret.x) / d4343;

        return true;
    }
}

