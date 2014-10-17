package com.mg.studio.alice.myframework.director;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.Log;

import com.mg.studio.alice.myframework.demotest.ScreenTest1;
import com.mg.studio.alice.myframework.resourcemanager.LruMGImageCache;
import com.mg.studio.alice.myframework.resourcemanager.MyImage;
import com.mg.studio.alice.myframework.resourcemanager.MyImageInfo;
import com.mg.studio.alice.myframework.resourcemanager.ResourceLoader;
import com.mg.studio.alice.myframework.type.MGSize;
import com.mg.studio.engine.MGCanvas;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;

/**
 * 
 */

/**
 * @author Dk Thach
 * 
 */
public class CanvasGame extends MGCanvas implements Runnable {
	public static MGSize sizeDevices = MGSize.zero();

	public static float widthDevices, heightDevices;
	private MyImage temp;
	private static Activity activity;

	// desired fps
	public final static int MAX_FPS = 60;
	// maximum number of frames to be skipped
	private final static int MAX_FRAME_SKIPS = 5;
	// the frame period
	private final static int FRAME_PERIOD = 1000 / MAX_FPS;
	float dt;
	long lastUpdate;
	private boolean isStop;
	Executor updateExe = Executors.newFixedThreadPool(1);

	public CanvasGame(Activity context) {
		super(context, true);
		activity = context;
		setRenderMode(RENDERMODE_WHEN_DIRTY);
		MGDirector.shareDirector().runWithScreen(ScreenTest1.screen());

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
						.copyFrom(MGImage.createImageFromAssets(getApp(),
								info.getAssetPath(), info.getScale(),
								info.isLinear()));
				Log.e("Load_Image : ", info.getAssetPath());
			}

		}

	}

	@Override
	public void run() {
		long beginTime;
		long timeDiff;
		int sleepTime = 0;
		int framesSkipped;
		while (!isStop) {

			framesSkipped = 0;
			beginTime = System.currentTimeMillis();
			dt = (beginTime - lastUpdate) * 0.001f;
			dt = Math.max(0, dt);
			lastUpdate = beginTime;
			try {
				MGDirector.shareDirector().update(dt);
			} catch (Throwable e) {

			}

			repaint();
			timeDiff = System.currentTimeMillis() - beginTime;
			sleepTime = (int) (FRAME_PERIOD - timeDiff);
			if (sleepTime > 0) {
				SystemClock.sleep(sleepTime);
			}
			while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
				try {
					MGDirector.shareDirector().update(dt);
				} catch (Throwable e) {
					// TODO: handle exception
				}

				sleepTime += FRAME_PERIOD;
				framesSkipped++;
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
		updateExe.execute(this);
		lastUpdate = System.currentTimeMillis();
		dt = 0;
		isStop = false;
		MGDirector.shareDirector().onResume();

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGCanvas#onPauseCanvas()
	 */
	@Override
	public void onPauseCanvas() {
		isStop = true;
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
