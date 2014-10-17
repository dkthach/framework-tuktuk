package com.mg.studio.tuktuk.director;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.tuktuk.actions.MGScheduler;
import com.mg.studio.tuktuk.transitions.MGTransitionScreen;

/**
 * @author Dk Thạch
 *
 */
/** MGDirector is class UI Manager **/
public class MGDirector {
	private static MGDirector instance = null;
	private MGScreen runningScreen_;
	private MGScreen nextScreen_;
	// Ngăn xếp quản lý screen
	List<MGScreen> screensStack_;
	private boolean sendCleanupToMGScreen;

	private MGDirector() {
		init();
	}

	public static MGDirector shareDirector() {
		if (instance == null) {
			instance = new MGDirector();
		}
		return instance;
	}

	public void onDestroy() {
		if (runningScreen_ != null) {
			runningScreen_.onDestroy();
		}
	}

	public void onPause() {
		if (runningScreen_ != null) {
			runningScreen_.onPause();

		}

	}

	public void onResume() {
		if (runningScreen_ != null) {
			runningScreen_.onResume();

		}

	}

	public boolean onBackPressed() {
		if (runningScreen_ != null) {
			return runningScreen_.onBackPressed();
		}
		return false;
	}

	public MGScreen getRunningScreen() {
		return runningScreen_;
	}

	private void init() {
		runningScreen_ = null;
		nextScreen_ = null;
		screensStack_ = new ArrayList<MGScreen>(10);
	}

	public void update(float dt) {
		if (runningScreen_ != null) {
			// runningScreen_.updateMeAndMyChild(dt);
			MGScheduler.sharedScheduler().tick(dt);

		}

	}

	public void render(MGGraphic g) {
		if (nextScreen_ != null) {
			setNextScreen();
		}

		if (runningScreen_ != null) {
			runningScreen_.paintSelfAndChild(g);

		}

	}

	public boolean getSendCleanupToScreen() {
		return sendCleanupToMGScreen;
	}

	/*** Dung de chay screen dau tien **/
	public void runWithScreen(MGScreen screen) {
		assert screen != null : "SCreen truyen vao can phai != null";
		assert runningScreen_ == null : "Khong the chay 1 screen khi screen khi da co screen dang chay, "
				+ "co the su dung pushscreen hoac replaceScreen ";

		pushScreen(screen);

	}

	/**
	 * Tạm dừng các runingscreen (các screen đang chạy)<br>
	 * đẩy screen mới vào ngăn xếp<br>
	 * và screen mới sẽ thực hiện
	 ***/
	public void pushScreen(MGScreen screen) {
		assert screen != null : "Screen truyen vao can phai != Null";
		sendCleanupToMGScreen = false;
		screensStack_.add(screen);
		nextScreen_ = screen;
	}

	/**
	 * Screen đang chạy hiện tại sẽ bị xóa, pop về screen truoc nó<br>
	 * như vậy phải tồn tại ít nhất 2 screen nếu không sẽ bị dừng
	 ***/
	public void popScreen() {
		assert runningScreen_ != null : "khong co screen nao truoc no dang chay";

		screensStack_.remove(screensStack_.size() - 1);
		int c = screensStack_.size();

		if (c == 0) {
			end();
		} else {
			nextScreen_ = screensStack_.get(c - 1);
		}

	}

	/**
	 * Thay thế 1 screen khác vào ngay vị trí screen hiện tại<br>
	 * screen hiện tại sẽ hủy<br>
	 * Chỉ gọi khi có Screen đang chạy
	 **/
	public void replaceScreen(MGScreen screen) {
		assert screen != null : "Screen truyen vao can phai != Null";

		int index = screensStack_.size();
		sendCleanupToMGScreen = true;
		screensStack_.set(index - 1, screen);

		nextScreen_ = screen;
	}

	private void end() {
		if (runningScreen_ != null) {
			runningScreen_.onExit();
			runningScreen_.cleanup();
			runningScreen_ = null;
		}
		nextScreen_ = null;
		screensStack_.clear();
	}

	public void setNextScreen() {
		boolean runningIsTransition = runningScreen_ instanceof MGTransitionScreen;
		boolean newIsTransition = nextScreen_ instanceof MGTransitionScreen;
		// neu ko co su dung Transition thi goi onExit ngay
		if (runningScreen_ != null && !newIsTransition) {
			runningScreen_.onExit();
		}
		// nhan thong bao va don dep neu khong se bi ro ri memory
		if (sendCleanupToMGScreen) {
			runningScreen_.cleanup();
		}
		runningScreen_ = nextScreen_;
		nextScreen_ = null;
		if (!runningIsTransition) {
			runningScreen_.onEnter();
			runningScreen_.onEnterTransitionDidFinish();
		}

	}

	public boolean onTouchDown1(PointF points) {
		if (runningScreen_ != null) {
			return runningScreen_.onTouchDown1(points);
		}
		return false;
	}

	public boolean onTouchDown2(PointF points) {
		if (runningScreen_ != null) {
			return runningScreen_.onTouchDown2(points);
		}
		return false;
	}

	public boolean onTouchUp1(PointF pointF) {
		if (runningScreen_ != null) {
			return runningScreen_.onTouchUp1(pointF);
		}
		return false;
	}

	public boolean onTouchUp2(PointF pointF) {
		if (runningScreen_ != null) {
			return runningScreen_.onTouchUp2(pointF);
		}
		return false;
	}

	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr) {
		if (runningScreen_ != null) {
			return runningScreen_.onTouchDrag1(xPre, yPre, xCurr, yCurr);
		}
		return false;
	}

	public boolean onTouchDrag2(float xPre, float yPre, float xCurr,
			float yCurr) {
		if (runningScreen_ != null) {
			return runningScreen_.onTouchDrag2(xPre, yPre, xCurr, yCurr);
		}
		return false;
	}

	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr, long eventDuration) {
		if (runningScreen_ != null) {
			runningScreen_.onTouchDrag1(xPre, yPre, xCurr, yCurr,
					eventDuration);
		}
		return false;

	}

	public boolean onTouchDrag2(float xPre, float yPre, float xCurr,
			float yCurr, long eventDuration) {
		if (runningScreen_ != null) {
			runningScreen_.onTouchDrag2(xPre, yPre, xCurr, yCurr,
					eventDuration);
		}
		return false;

	}

}
