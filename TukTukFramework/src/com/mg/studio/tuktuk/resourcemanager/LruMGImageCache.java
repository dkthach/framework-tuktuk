package com.mg.studio.tuktuk.resourcemanager;

import java.util.Map;

import android.support.v4.util.LruCache;
import android.util.Log;




public class LruMGImageCache extends LruCache<String, MyImage> {

  
private static LruMGImageCache instance = null;

    protected LruMGImageCache(int maxSize) {
        super(maxSize);
        Log.d("LruTexTureCache", "LruTexTureCache");
    }

    public static LruMGImageCache getInstance() {
        if (null == instance) {
            instance = new LruMGImageCache(200);

        }
        return instance;
    }

    public void reset() {
        Map<String, MyImage> lTexture = instance.getValue();
        if (lTexture != null) {
            for (String s : lTexture.keySet()) {
                LruMGImageCache.getInstance().get(s).disposeGLES2();
            }
        }
        evictAll();
        System.gc();
    }

    public Map<String, MyImage> getValue() {
        return instance.snapshot();
    }

    @Override
    protected MyImage create(String key) {
        String path = key.substring(0, key.lastIndexOf("?"));
        float scale = Float.parseFloat(key.substring(key.lastIndexOf('?') + 1));
        return new MyImage(path, scale);
    }

    public void releaseAll() {
        Map<String, MyImage> lTexture = instance.getValue();
        if (lTexture != null) {
            for (String s : lTexture.keySet()) {
                LruMGImageCache.getInstance().get(s).setReady(false);
                LruMGImageCache.getInstance().get(s).disposeGLES2();
                Log.e("RS_Release", s);
            }
        }
    }
}
