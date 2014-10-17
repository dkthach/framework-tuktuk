package com.mg.studio.tuktuk.events;

import com.mg.studio.engine.MGColor;



/// CC RGBA protocol
public interface MGRGBAProtocol {
    /** sets Color
     
     */
    public void setColor(MGColor color);

    /** returns the color
   
     */
    public MGColor getColor();

    /// returns the opacity
    public float getOpacity();

    /** sets the opacity.
     @warning If the the texture has premultiplied alpha then, the R, G and B channels will be modifed.
     Values goes from 0 to 255, where 255 means fully opaque.
     */
    public void setOpacity(float opacity);

    /** sets the premultipliedAlphaOpacity property.
     If set to NO then opacity will be applied as: glColor(R,G,B,opacity);
     If set to YES then oapcity will be applied as: glColor(opacity, opacity, opacity, opacity );
     Textures with premultiplied alpha will have this property by default on YES.
        Otherwise the default value is NO
    
     */
    public void setOpacityModifyRGB(boolean b);

    /** returns whether or not the opacity will be applied using glColor(R,G,B,opacity)
        or glColor(opacity, opacity, opacity, opacity);
    
     */
    public boolean doesOpacityModifyRGB();
}

