package com.mg.studio.tuktuk.type;


/** bezier configuration structure
 */
public class MGBezierConfig {
    //! end position of the bezier
    public MGPointF endPosition;
    //! Bezier control point 1
    public MGPointF controlPoint_1;
    //! Bezier control point 2
    public MGPointF controlPoint_2;
    
    public MGBezierConfig() {
    	
    }

    // Bezier cubic formula:
    // 	((1 - t) + t)3 = 1 
    // Expands to 
    //   (1 - t)3 + 3t(1-t)2 + 3t2(1 - t) + t3 = 1 
    public static float bezierAt( float a, float b, float c, float d, float t) {
    	return (float)(Math.pow(1-t,3) * a
    			+ 3*t*(Math.pow(1-t,2)) * b
    			+ 3*Math.pow(t,2)*(1-t) * c
    			+ Math.pow(t,3) * d);
    }

}
