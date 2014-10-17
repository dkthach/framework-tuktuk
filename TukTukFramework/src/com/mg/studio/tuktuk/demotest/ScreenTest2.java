/**
 * 
 */
package com.mg.studio.tuktuk.demotest;

import android.graphics.PointF;

import com.google.android.gms.internal.cn;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.ease.MGEaseBounceOut;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.instant.MGCallbackN;
import com.mg.studio.tuktuk.actions.interval.MGMoveTo;
import com.mg.studio.tuktuk.actions.interval.MGScaleTo;
import com.mg.studio.tuktuk.actions.interval.MGSequence;
import com.mg.studio.tuktuk.actions.interval.MGSpawn;
import com.mg.studio.tuktuk.director.CanvasGame;
import com.mg.studio.tuktuk.director.MGDirector;
import com.mg.studio.tuktuk.director.MGNode;
import com.mg.studio.tuktuk.director.MGScreen;
import com.mg.studio.tuktuk.transitions.MGZoomInBounceOutTransiton;
import com.mg.studio.tuktuk.transitions.MGZoomInExponentialOutTransiton;
import com.mg.studio.tuktuk.type.MGPointF;

/**
 * @author Dk Thach
 *
 */
public class ScreenTest2 extends MGScreen {
	public static MGScreen screen() {
		return new ScreenTest2();
	}

	private ScreenTest2() {

	}

	@Override
	public void drawRearChild(MGGraphic g) {
		g.setColor(1f, 0.5f, 0.5f, 0);
		g.drawRect(20 * RM.rate, 20 * RM.rate, CanvasGame.widthDevices - 20
				* RM.rate, CanvasGame.heightDevices - 20 * RM.rate, true);
	}

	void addball(float x, float y) {
		Ball ball = new Ball(RM.ballBlue);
		ball.setAnchorPoint(0.5f, 0.5f);
		ball.setPosition(x, y);
		// di chuyen leen
		MGMoveTo moveball = MGMoveTo.action(3f, new MGPointF(x, 0));
		// tạo nẩy cho moveball
		MGEaseBounceOut bounceOut = MGEaseBounceOut.action(moveball);
		MGScaleTo scaleTo = MGScaleTo.action(3f, 0.5f);
		// trộn 2 action chạy cùng lúc
		MGSpawn spawn = MGSpawn.actions(scaleTo, bounceOut);

		// action gọi hàm
		MGCallbackN callback = MGCallbackN.action(ball, new ActionCallback() {

			@Override
			public void execute(Object object) {
				Ball ball = (Ball) object;
				ball.removeSelf();
			}
		});

		// hàng đợi nhiều action chạy xong gọi tự hủy
		MGSequence sequence = MGSequence.actions(spawn, callback);
		ball.runAction(sequence);
		addChild(ball);

	}

	@Override
	public boolean onTouchDown1(PointF points) {
		addball(points.x, points.y);
		return true;
	}

	@Override
	public boolean onBackPressed() {
		MGDirector.shareDirector().replaceScreen(
				MGZoomInExponentialOutTransiton.transition(1.5f,
						ScreenTest1.screen()));
		return true;
	}
}
