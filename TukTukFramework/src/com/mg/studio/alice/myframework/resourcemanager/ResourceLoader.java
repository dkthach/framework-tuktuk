package com.mg.studio.alice.myframework.resourcemanager;

import java.util.ArrayList;

import com.mg.studio.engine.MGImage;

public class ResourceLoader {

	public static ArrayList<MyImageInfo> resourcelLists = new ArrayList<MyImageInfo>();
	/***Custom linear**/
	public static MGImage load(String assetPath, float scale, boolean linear) {
		resourcelLists.add(new MyImageInfo(assetPath, scale, linear));
		String key = assetPath + "?" + scale;
		return LruMGImageCache.getInstance().get(key);
	}
	
	/***linear  =  Fale**/
	public static MGImage load(String assetPath, float scale) {
		resourcelLists.add(new MyImageInfo(assetPath, scale, false));
		String key = assetPath + "?" + scale;
		return LruMGImageCache.getInstance().get(key);
	}
}
