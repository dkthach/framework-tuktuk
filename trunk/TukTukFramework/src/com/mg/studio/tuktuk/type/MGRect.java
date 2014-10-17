package com.mg.studio.tuktuk.type;

public class MGRect {
    public MGPointF origin;
    public MGSize size;

    public MGRect() {
        this(0, 0, 0, 0);
    }

    public MGRect(final MGPointF origin, final MGSize size) {
        this(origin.x, origin.y, size.width, size.height);
    }
    
    private static final MGRect ZERO_RECT = new MGRect(0, 0, 0, 0);
    public static MGRect getZero() {
    	return ZERO_RECT;
    }
    
    public static MGRect zero() {
        return new MGRect(0, 0, 0, 0);
    }

    public static MGRect make(final MGPointF origin, final MGSize size) {
        return new MGRect(origin.x, origin.y, size.width, size.height);
    }

    public static MGRect make(float x, float y, float w, float h) {
        return new MGRect(x, y, w, h);
    }
    
    public static MGRect make(MGRect r) {
    	return new MGRect(r.origin, r.size);
    }

    private MGRect(float x, float y, float w, float h) {
        origin = MGPointF.ccp(x, y);
        size = MGSize.make(w, h);
    }
    
	public void set(MGRect r) {
		origin.set(r.origin);
		size.set(r.size);
	}

	public void set(float x, float y, float w, float h) {
		origin.set(x, y);
		size.set(w, h);
	}

    public boolean contains(float x, float y) {
        return size.width > 0 && size.height > 0  // check for empty first
                && x >= origin.x && x < (origin.x + size.width)
                && y >= origin.y && y < (origin.y + size.height);
    }

    public String toString() {
        return "((" + origin.x + ", " + origin.y + "),(" + size.width + ", " + size.height + "))";
    }


    public static boolean equalToRect(final MGRect r1, final MGRect r2) {
        return MGPointF.equalToPoint(r1.origin, r2.origin) && MGSize.equalToSize(r1.size, r2.size);
    }

    /**
     * Returns true if aPoint is inside aRect.
     */
    public static boolean containsPoint(final MGRect aRect, final MGPointF aPoint) {
        return ((aPoint.x >= minX(aRect))
                && (aPoint.y >= minY(aRect))
                && (aPoint.x < maxX(aRect))
                && (aPoint.y < maxY(aRect)));
    }

    public static boolean containsRect(final MGRect aRect, final MGRect bRect) {
        return (!isEmptyRect(bRect)
                && (minX(aRect) <= minX(bRect))
                && (minY(aRect) <= minY(bRect))
                && (maxX(aRect) >= maxX(bRect))
                && (maxY(aRect) >= maxY(bRect)));
    }
    
    public static boolean intersects(MGRect a, MGRect b)
    {
    	return (a.origin.x >= (b.origin.x - a.size.width) && a.origin.x <= (b.origin.x - a.size.width) + (b.size.width + a.size.width)
    			&& a.origin.y >= (b.origin.y - a.size.height) && a.origin.y <= (b.origin.y - a.size.height) + (b.size.height + a.size.height));
    }

    public static MGRect applyAffineTransform(MGRect aRect, MGAffineTransform matrix) {
        MGRect r = MGRect.make(0, 0, 0, 0);
        MGPointF[] p = new MGPointF[4];

        for (int i = 0; i < 4; i++) {
            p[i] = MGPointF.make(aRect.origin.x, aRect.origin.y);
        }

        p[1].x += aRect.size.width;
        p[2].y += aRect.size.height;
        p[3].x += aRect.size.width;
        p[3].y += aRect.size.height;

        for (int i = 0; i < 4; i++) {
            p[i] = MGPointF.applyAffineTransform(p[i], matrix);
        }

        MGPointF min = MGPointF.make(p[0].x, p[0].y),
                max = MGPointF.make(p[0].x, p[0].y);
        for (int i = 1; i < 4; i++) {
            min.x = Math.min(min.x, p[i].x);
            min.y = Math.min(min.y, p[i].y);
            max.x = Math.max(max.x, p[i].x);
            max.y = Math.max(max.y, p[i].y);
        }

        r.origin.x = min.x; r.origin.y = min.y;
        r.size.width = max.x - min.x; r.size.height = max.y - min.y;

        return r;
    }

    /**
     * Returns the greatest x-coordinate value still inside aRect.
     */
    public static float maxX(final MGRect aRect) {
        return aRect.origin.x + aRect.size.width;
    }

    /**
     * Returns the greatest y-coordinate value still inside aRect.
     */
    public static float maxY(final MGRect aRect) {
        return aRect.origin.y + aRect.size.height;
    }

    /**
     * Returns the x-coordinate of aRect's middle point.
     */
    public static float midX(MGRect aRect) {
        return aRect.origin.x + (float) (aRect.size.width / 2.0);
    }

    /**
     * Returns the y-coordinate of aRect's middle point.
     */
    public static float midY(final MGRect aRect) {
        return aRect.origin.y + (float) (aRect.size.height / 2.0);
    }

    /**
     * Returns the least x-coordinate value still inside aRect.
     */
    public static float minX(final MGRect aRect) {
        return aRect.origin.x;
    }

    /**
     * Returns the least y-coordinate value still inside aRect.
     */
    public static float minY(final MGRect aRect) {
        return aRect.origin.y;
    }

    /**
     * Returns aRect's width.
     */
    public static float width(final MGRect aRect) {
        return aRect.size.width;
    }

    /**
     * Returns aRect's height.
     */
    public static float height(final MGRect aRect) {
        return aRect.size.height;
    }

    public static boolean isEmptyRect(MGRect aRect) {
        return (!(aRect.size.width > 0 && aRect.size.height > 0));
    }

    public enum Edge {
        MinXEdge,
        MinYEdge,
        MaxXEdge,
        MaxYEdge,
    }

    private static MGRect sRect = new MGRect();
    private static MGRect rRect = new MGRect();

    public static void divideRect(final MGRect aRect, MGRect[] slice, MGRect[] remainder, float amount, MGRect.Edge edge) {

        if (slice == null) {
            slice = new MGRect[1];
        	slice[0] = sRect;
        }

        if (remainder == null) {
        	remainder = new MGRect[1];
            remainder[0] = rRect;
        }

        if (isEmptyRect(aRect)) {
            slice[0] = MGRect.make(0, 0, 0, 0);
            remainder[0] = MGRect.make(0, 0, 0, 0);
            return;
        }

        switch (edge) {
            case MinXEdge:
                if (amount > aRect.size.width) {
                    slice[0] = aRect;
                    remainder[0] = MGRect.make(maxX(aRect),
                            aRect.origin.y,
                            0,
                            aRect.size.height);
                } else {
                    slice[0] = MGRect.make(aRect.origin.x,
                            aRect.origin.y,
                            amount,
                            aRect.size.height);
                    remainder[0] = MGRect.make(maxX(slice[0]),
                            aRect.origin.y,
                            maxX(aRect) - maxX(slice[0]),
                            aRect.size.height);
                }
                break;
            case MinYEdge:
                if (amount > aRect.size.height) {
                    slice[0] = aRect;
                    remainder[0] = MGRect.make(aRect.origin.x,
                            maxY(aRect),
                            aRect.size.width, 0);
                } else {
                    slice[0] = MGRect.make(aRect.origin.x,
                            aRect.origin.y,
                            aRect.size.width,
                            amount);
                    remainder[0] = MGRect.make(aRect.origin.x,
                            maxY(slice[0]),
                            aRect.size.width,
                            maxY(aRect) - maxY(slice[0]));
                }
                break;
            case MaxXEdge:
                if (amount > aRect.size.width) {
                    slice[0] = aRect;
                    remainder[0] = MGRect.make(aRect.origin.x,
                            aRect.origin.y,
                            0,
                            aRect.size.height);
                } else {
                    slice[0] = MGRect.make(maxX(aRect) - amount,
                            aRect.origin.y,
                            amount,
                            aRect.size.height);
                    remainder[0] = MGRect.make(aRect.origin.x,
                            aRect.origin.y,
                            minX(slice[0]) - aRect.origin.x,
                            aRect.size.height);
                }
                break;
            case MaxYEdge:
                if (amount > aRect.size.height) {
                    slice[0] = aRect;
                    remainder[0] = MGRect.make(aRect.origin.x,
                            aRect.origin.y,
                            aRect.size.width,
                            0);
                } else {
                    slice[0] = MGRect.make(aRect.origin.x,
                            maxY(aRect) - amount,
                            aRect.size.width,
                            amount);
                    remainder[0] = MGRect.make(aRect.origin.x,
                            aRect.origin.y,
                            aRect.size.width,
                            minY(slice[0]) - aRect.origin.y);
                }
                break;
            default:
                break;
        }
    }
}
