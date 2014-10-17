package com.mg.studio.alice.myframework.resourcemanager;

public class MyImageInfo {

    String assetPath;
    float  scale;
    boolean linear;

    public MyImageInfo(String assetPath, float scale,boolean linear) {
        super();
        this.assetPath = assetPath;
        this.scale = scale;
        this.linear =  linear;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public float getScale() {
        return scale;
    }
    public boolean isLinear() {
	return linear;
}
}
