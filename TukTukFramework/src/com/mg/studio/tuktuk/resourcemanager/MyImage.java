package com.mg.studio.tuktuk.resourcemanager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.mg.studio.engine.MGCanvas;
import com.mg.studio.engine.MGImage;
import com.mg.studio.tuktuk.director.CanvasGame;

public class MyImage extends MGImage
{

    private float  scale;
    private String stringpath;

    public MyImage(String path, float scale)
    {
        this.scale = scale;
        this.stringpath = path;
        if (scale <= 0)
        {
            throw new RuntimeException("Scale value : " + scale);
        }
        InputStream stream;
        try
        {
            stream = CanvasGame.getApp().getAssets().open(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("KhÃ´ng táº¡o Ä‘Æ°á»£c Image " + path);
        }
        if (stream == null)
        {
            throw new RuntimeException("input steam error");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (scale != 1)
        {
            BitmapFactory.decodeStream(stream, null, options);
            widthContent = calculateSampleSize(options.outWidth, scale);
            heightContent = calculateSampleSize(options.outHeight, scale);
        }
        else
        {
            BitmapFactory.decodeStream(stream, null, options);
            widthContent = options.outWidth;
            heightContent = options.outHeight;
        }
        if (MGCanvas.isNpot())
        {
            widthNpot = widthContent;
            heightNpot = heightContent;
        }
        else
        {
            widthNpot = nextPowerOf2(widthContent);
            heightNpot = nextPowerOf2(heightContent);
        }
        try
        {
            stream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public MyImage(MGCanvas canvas, Bitmap bitmap, boolean linear)
    {
        this.linear = linear;
        widthContent = bitmap.getWidth();
        heightContent = bitmap.getHeight();
        if (MGCanvas.isNpot())
        {
            widthNpot = widthContent;
            heightNpot = heightContent;
            data = bitmap;
        }
        else
        {
            int w2 = nextPowerOf2(widthContent);
            int h2 = nextPowerOf2(heightContent);
            widthNpot = w2;
            heightNpot = h2;
            if (w2 == widthContent && h2 == heightContent)
            {
                data = bitmap;
            }
            else
            {
                Bitmap t = extractBitmap(bitmap, w2, h2);
                data = t;
            }
        }
        canvas.queueEvent(new Runnable()
        {

            public void run()
            {
                // TODO Auto-generated method stub
                int[] textureIds = new int[1];
                GLES20.glGenTextures(1, textureIds, 0);
                textureId = textureIds[0];
                initTexture(data);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
                ready = true;
                data.recycle();

            }
        });

    }

    public void load()
    {
	    
	    
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        Bitmap bitmap;
        try
        {
            InputStream stream = CanvasGame.getApp().getAssets().open(stringpath);
            if (scale != 1)
            {
                bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(stream),
                                                   widthContent,
                                                   heightContent,
                                                   true);
            }
            else
            {
                bitmap = BitmapFactory.decodeStream(stream);
            }
            if (MGCanvas.isNpot())
            {
                initTexture(bitmap);
            }
            else
            {
                if (widthNpot == widthContent && heightNpot == heightContent)
                {
                    initTexture(bitmap);
                    bitmap.recycle();
                }
                else
                {
                    Bitmap t = extractBitmap(bitmap, (int) widthNpot, (int) heightNpot);
                    initTexture(t);
                    t.recycle();
                }
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            setReady(true);
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void setReady(boolean b)
    {
        ready = b;
    }
}
