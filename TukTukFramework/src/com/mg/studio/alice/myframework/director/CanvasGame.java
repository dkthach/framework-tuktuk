package com.mg.studio.alice.myframework.director;

import android.app.Activity;
import android.graphics.PointF;
import android.util.Log;

import com.mg.studio.alice.myframework.resourcemanager.LruMGImageCache;
import com.mg.studio.alice.myframework.resourcemanager.MyImage;
import com.mg.studio.alice.myframework.resourcemanager.MyImageInfo;
import com.mg.studio.alice.myframework.resourcemanager.ResourceLoader;
import com.mg.studio.alice.myframework.type.MGSize;
import com.mg.studio.engine.MGCanvas;
import com.mg.studio.engine.MGGameActivity;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.engine.MGStandardGameActivity;

/**
 * 
 */

/**
 * @author Dk Thach
 * 
 */
public class CanvasGame extends MGCanvas {
	public static MGSize sizeDevices = MGSize.zero();

	public static float widthDevices, heightDevices;
	private MyImage temp;
	private static Activity activity;

	public CanvasGame(Activity context) {
		super(context, true);
		activity = context;
		setRenderMode(RENDERMODE_WHEN_DIRTY);

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onCreateCanvas()
	 */
	@Override
	public void onCreateCanvas() {
		widthDevices = getWidth();
		heightDevices = getHeight();
		sizeDevices.set(getScreenWidth(), getScreenHeight());

	}

	public static Activity getApp() {
		return activity;
	}

	@Override
	public void paint(MGGraphic g) {
		g.enableBlendFunc();
		g.setColor(1f, 0f, 0f, 0f);
		g.fillScreen();
		MGDirector.shareDirector().render(g);
		MGDialogManager.shareManager().render(g);
		if (ResourceLoader.resourcelLists.size() > 0) {
			if ((temp == null || temp.isReady())) {
				final MyImageInfo info = ResourceLoader.resourcelLists
						.remove(0);
				final String key = info.getAssetPath() + "?"
						+ info.getScale();
				temp = LruMGImageCache.getInstance().get(key);
				LruMGImageCache
						.getInstance()
						.get(key)
						.copyFrom(MGImage.createImageFromAssets(
								getApp(),
								info.getAssetPath(), info.getScale(),
								info.isLinear()));
				Log.e("Load_Image : ", info.getAssetPath());
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onDestroyCanvas()
	 */
	@Override
	public void onDestroyCanvas() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onResumeCanvas()
	 */
	@Override
	public void onResumeCanvas() {
		MGDirector.shareDirector().onResume();

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onPauseCanvas()
	 */
	@Override
	public void onPauseCanvas() {
		MGDirector.shareDirector().onPause();

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onChangeCanvas()
	 */
	@Override
	public void onChangeCanvas() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onTouchDown1(android.graphics.PointF)
	 */
	@Override
	public void onTouchDown1(PointF point) {
		if (!MGDialogManager.shareManager().onTouchDown1(point)) {
			MGDirector.shareDirector().onTouchDown1(point);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onTouchDown2(android.graphics.PointF)
	 */
	@Override
	public void onTouchDown2(PointF point) {
		if (!MGDialogManager.shareManager().onTouchDown2(point)) {
			MGDirector.shareDirector().onTouchDown2(point);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onTouchUp1(android.graphics.PointF)
	 */
	@Override
	public void onTouchUp1(PointF point) {
		if (!MGDialogManager.shareManager().onTouchUp1(point)) {
			MGDirector.shareDirector().onTouchUp1(point);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onTouchUp2(android.graphics.PointF)
	 */
	@Override
	public void onTouchUp2(PointF point) {

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onTouchDrag1(float, float, float,
	 * float, long)
	 */
	@Override
	public void onTouchDrag1(float xPre, float yPre, float xCurr, float yCurr,
			long eventDuration) {
		if (!MGDialogManager.shareManager().onTouchDrag1(xPre, yPre, xCurr,
				yCurr)) {
			MGDirector.shareDirector().onTouchDrag1(xPre, yPre, xCurr,
					yCurr, eventDuration);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onTouchDrag2(float, float, float,
	 * float, long)
	 */
	@Override
	public void onTouchDrag2(float xPre, float yPre, float xCurr, float yCurr,
			long eventDuration) {

	}

}
