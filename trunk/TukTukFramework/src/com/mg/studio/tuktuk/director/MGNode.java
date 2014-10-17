package com.mg.studio.tuktuk.director;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.tuktuk.actions.MGActionManager;
import com.mg.studio.tuktuk.actions.MGScheduler;
import com.mg.studio.tuktuk.actions.UpdateCallback;
import com.mg.studio.tuktuk.actions.bas.MGAction;
import com.mg.studio.tuktuk.type.MGPointF;
import com.mg.studio.tuktuk.type.MGSize;

/**
 * @author DK Thach
 * 
 */

public class MGNode {
	protected PointF anchorPoint_;
	protected PointF anchorPointInPixels_;
	protected MGPointF position_;
	protected MGSize contentSize_;
	protected MGNode parent_;
	protected List<MGNode> children_;
	private int tag_;

	/**
	 * (1)nếu TRUE thì position trùng lên anchorPoint các Object trong game nên
	 * sử dụng .(2)Riêng Screen hoặc Layer nên set lại giá trị FALSE để
	 * position lên góc trái phía trên. Cả 2 trường hợp trên cho dù TRUE hay
	 * FALSE thì các transForm (scale, rotale..) đều lấy anchorpoint làm gốc
	 **/
	private boolean isRelativeAnchorPoint;
	// z-order value
	protected int zOrder_ = -1;
	private boolean isRunning_;
	private boolean hide_;
	private boolean transFormEnable = true;

	protected float scaleX_;
	protected float scaleY_;
	protected float rotation_;

	protected RectF contentRect;

	public static final int TAG_INVALID = -1;

	public static MGNode node() {
		return new MGNode();
	}

	protected MGNode() {
		init();
	}

	// set Defaul value
	private void init() {
		anchorPoint_ = new PointF(0, 0);
		anchorPointInPixels_ = new PointF(0, 0);
		position_ = MGPointF.make(0, 0);
		contentSize_ = MGSize.zero();
		tag_ = TAG_INVALID;
		parent_ = null;
		children_ = null;
		isRelativeAnchorPoint = true;
		isRunning_ = false;
		scaleX_ = 1.0f;
		scaleY_ = 1.0f;
		contentRect = new RectF();
	}

	public void setHide(boolean hide_) {
		this.hide_ = hide_;
	}

	public boolean isHide() {
		return hide_;
	}

	public float getScaleX() {
		return scaleX_;
	}

	public void setScaleX(float sX) {
		this.scaleX_ = sX;
	}

	public void setScaleY(float sY) {
		this.scaleY_ = sY;
	}

	public float getScaleY() {
		return scaleY_;
	}

	public void setScale(float s) {
		scaleX_ = scaleY_ = s;

	}

	public float getScale() {
		if (scaleX_ == scaleY_) {
			return scaleX_;
		} else {
			Log.w("MGNODE",
					"MGNode#scale. ScaleX != ScaleY. Don't know which one to return");
		}
		return 0;
	}

	public void setRotation(float angle) {
		this.rotation_ = angle;
	}

	public float getRotation() {
		return rotation_;
	}

	/**
	 * (1)nếu TRUE thì position trùng lên anchorPoint các Object trong game nên
	 * sử dụng .(2)Riêng Screen hoặc Layer nên set lại giá trị FALSE để
	 * position lên góc trái phía trên. Cả 2 trường hợp trên cho dù TRUE hay
	 * FALSE thì các transForm (scale, rotale..) đều lấy anchorpoint làm gốc
	 * Mặc định TRUE
	 **/
	public void setRelativeAnchorPoint(boolean newValue) {
		this.isRelativeAnchorPoint = newValue;
	}

	/** Node có đang hoạt động hay không **/
	public boolean isRunning() {
		return isRunning_;
	}

	public void onEnter() {
		if (children_ != null)
			try {
				for (MGNode chil : children_) {
					chil.onEnter();
				}
			} catch (Throwable e) {
				// TODO: handle exception
			}
		resumeSchedulerAndActions();
		isRunning_ = true;
	}

	public void onExit() {
		if (children_ != null)
			try {
				for (MGNode chil : children_) {
					chil.onExit();
				}
			} catch (Throwable e) {
				// TODO: handle exception
			}
		pauseSchedulerAndActions();
		isRunning_ = false;
	}

	// GET SET ANCHOR POINT*****************************

	public void setAnchorPoint(float x, float y) {
		if (!(x == anchorPoint_.x && y == anchorPoint_.y)) {
			anchorPoint_.set(x, y);
			anchorPointInPixels_.set(x * contentSize_.width, y
					* contentSize_.height);
			contentRect.set(position_.x - anchorPointInPixels_.x,
					position_.y - anchorPointInPixels_.y, position_.x
							- anchorPointInPixels_.x
							+ contentSize_.width, position_.y
							- anchorPointInPixels_.y
							+ contentSize_.height);
		}
	}

	/**
	 * AnchorPoint là điểm neo để vẽ MGnode này--<br>
	 * 
	 * (0,0) có nghĩa là góc trên cùng bên trái và (1,1) có nghĩa là góc dưới
	 * bên phải. (0.5,0.5) là neo tại trung tâm của MGnode, có thể sử dụng các
	 * giá trị cao hơn (1,1) và thấp hơn (0,0) . Mặc định anchorPoint là (0,0).<br>
	 * 
	 * ---cac anchor thông dụng:<br>
	 * 
	 * -- ANCHOR_CENTER = (0.5f, 0.5f); <br>
	 * -- ANCHOR_LEFT_TOP = (0, 0); <br>
	 * -- ANCHOR_LEFT_BOTTOM = (0, 1); <br>
	 * -- ANCHOR_RIGHT_BOTTOM =(1, 1);<br>
	 * -- ANCHOR_RIGHT_TOP = (1, 0)
	 */
	public void setAnchorPoint(PointF point) {
		setAnchorPoint(point.x, point.y);
	}

	public PointF getAnchorPoint() {
		return anchorPoint_;
	}

	public PointF getAnchorPointInPixel() {
		return anchorPointInPixels_;
	}

	// GET SET CONTENT SIZE

	public void setContentSize(float w, float h) {
		if (!(contentSize_.width == w && contentSize_.height == h)) {
			contentSize_.set(w, h);
			anchorPointInPixels_.set(contentSize_.width * anchorPoint_.x,
					contentSize_.height * anchorPoint_.y);
			contentRect.set(position_.x - anchorPointInPixels_.x,
					position_.y - anchorPointInPixels_.y, position_.x
							- anchorPointInPixels_.x + w, position_.y
							- anchorPointInPixels_.y + h);

		}

	}

	public RectF getContentRect() {
		return contentRect;
	}

	public void setContentSize(MGSize size) {
		setContentSize(size.width, size.height);
	}

	public MGSize getContentSize() {
		return contentSize_;
	}

	// GET SET POSITION
	public void setPosition(float x, float y) {
		contentRect.set(x - anchorPointInPixels_.x, y
				- anchorPointInPixels_.y, x - anchorPointInPixels_.x
				+ contentSize_.width, y - anchorPointInPixels_.y
				+ contentSize_.height);
		position_.set(x, y);

	}

	public void setPosition(PointF position_) {
		setPosition(position_.x, position_.y);
	}

	public MGPointF getPosition() {
		return position_;
	}

	// GET SET TAG

	public int getTag() {
		return tag_;
	}

	public void setTag(int tag_) {
		this.tag_ = tag_;
	}

	public MGNode getParent() {
		return parent_;
	}

	public void setParent(MGNode parent_) {
		this.parent_ = parent_;
	}

	public int getZOrder() {
		return zOrder_;
	}

	private void _setZOrder(int z) {
		zOrder_ = z;
	}

	public List<MGNode> getChildren() {
		return children_;
	}

	public MGNode getChildByIndex(int index) {
		return children_.get(index);

	}

	private void childrenAlloc() {
		children_ = Collections.synchronizedList(new ArrayList<MGNode>());
	}

	// sử dụng để sắp xếp object trong list
	private static Comparator<MGNode> zOrderComparator = new Comparator<MGNode>() {
		@Override
		public int compare(MGNode o1, MGNode o2) {
			return o1.zOrder_ - o2.zOrder_;
		}
	};

	private void insertChild(MGNode node, int z) {
		node._setZOrder(z);
		try {

			int ind = Collections.binarySearch(children_, node,
					zOrderComparator);
			// tim index
			if (ind >= 0) {
				int size = children_.size();
				MGNode prev;

				do {
					prev = children_.get(ind);
					ind++;
				} while (ind < size
						&& children_.get(ind).zOrder_ == prev.zOrder_);
			} else { // index khong tim thay
				ind = -(ind + 1);
			}
			children_.add(ind, node);
		} catch (Throwable e) {
		}

	}

	/**
	 * @param child
	 *             dạng MGNode
	 * @param z
	 *             Thứ tự paint, Z nhỏ paint trước...(Z>=1)
	 * @param tag
	 *             gắng thẻ để dễ xử lý và phân loại đối tượng
	 * @return this
	 */
	public MGNode addChild(MGNode child, int z, int tag) {
		assert child != null : "Node Child can phai .. != nul";
		assert child.parent_ == null : "Child Node da co roi khong duoc add lai nua!";
		if (children_ == null)
			childrenAlloc();
		insertChild(child, z);
		child.tag_ = tag;
		child.setParent(this);
		if (isRunning_) {
			child.onEnter();
		}
		return this;
	}

	/**
	 * @param child
	 *             dạng Node
	 * @param z
	 *             Thứ tự paint Z>= 1 nhỏ paint trước...
	 * 
	 * @return this
	 */
	public MGNode addChild(MGNode child, int z) {
		assert child != null : "Node Child can phai .. != null";

		return addChild(child, z, child.tag_);
	}

	public MGNode addChild(MGNode child) {
		assert child != null : "Node Child can phai .. != null";

		return addChild(child, child.zOrder_, child.tag_);
	}

	/** tự remove chính nó **/
	private void removeFromParent(boolean doCleanup) {
		if (this.parent_ != null) {
			this.parent_.removeChild(this, doCleanup);
		}
	}

	/** tự remove chính nó va clear allaction **/
	public void removeSelf() {
		this.removeFromParent(true);
	}

	/** remove node dựa vào tên node **/
	public void removeChild(MGNode child, boolean doCleanup) {

		if (child == null)
			return;

		if (children_.contains(child))
			detachChild(child, doCleanup);
	}

	private void detachChild(MGNode child, boolean doCleanup) {
		if (isRunning_) {
			child.onExit();
		}
		// If you don't do cleanup, the child's actions will not get removed
		// and the
		// its scheduledSelectors_ dict will not get released!
		if (doCleanup) {
			child.cleanup();
		}
		child.setParent(null);

		children_.remove(child);
	}

	/**
	 * gở node dựa vào số thẻ tag
	 */
	public void removeChildByTag(int tag, boolean doCleanup) {
		assert tag != TAG_INVALID : "Tag khong hop le!";

		MGNode child = getChildByTag(tag);
		if (child == null) {
			// Log.w(LOG_TAG, "removeChild: child not found");
		} else {
			removeChild(child, doCleanup);
		}
	}

	/**
	 * gở hết các node con
	 */
	public void removeAllChildren(boolean cleanup) {
		// khong su dung deteatachChild su dung phương thức nay nhanh hơn
		if (children_ == null)
			return;

		for (int i = 0; i < children_.size(); ++i) {
			MGNode child = children_.get(i);
			if (isRunning_) {
				child.onExit();
			}
			if (cleanup) {
				child.cleanup();
			}
			child.setParent(null);
		}
		children_.clear();

	}

	/**
	 * Lấy node con dựa vào số thẻ Tag của nó
	 * 
	 * @return returns a MGNode object
	 */
	public MGNode getChildByTag(int tag) {
		assert tag != TAG_INVALID : "Invalid tag_";

		if (children_ != null)
			for (int i = 0; i < children_.size(); ++i) {
				MGNode node = children_.get(i);
				if (node.tag_ == tag) {
					return node;
				}
			}

		return null;
	}

	/**
	 * sắp xếp lại thứ tự node con theo chỉ số order mới<br>
	 * , yêu cầu node này phải được add vào đây rồi
	 */
	public void reorderChild(MGNode child, int zOrder) {
		assert child != null : "Child must be non-null";
		children_.remove(child);
		this.insertChild(child, zOrder);

	}

	public PointF convertPoint(PointF parentpointF) {
		PointF point = new PointF();
		point.x = parentpointF.x - (position_.x - anchorPointInPixels_.x);
		point.y = parentpointF.y - (position_.y - anchorPointInPixels_.y);
		return point;
	}

	public MGPointF reversePoint(MGPointF childPoint) {
		MGPointF pointF = MGPointF.zero();
		pointF.x = childPoint.x + position_.x- anchorPointInPixels_.x;
		pointF.y = childPoint.y + position_.y- anchorPointInPixels_.y;
		return pointF;
	}

	/***
	 * Vẽ thêm tùy ý ngoài list Node con thì overwrite lại hàm này <br>
	 * luôn luôn vẽ đè trên Node con<br>
	 * Thứ tự vẽ trong như sau<br>
	 * drawRear()->>>Draw listnode--->>>DrawFront()
	 **/
	public void drawFrontChild(MGGraphic g) {
		// g.setColor(255, 255, 0, 0);
		// g.drawRect(position_.x, position_.y, contentSize_.width,
		// contentSize_.height);
	}

	/***
	 * overwrite lại hàm này <br>
	 * luôn luôn vẽ phía sau tất cả Node con<br>
	 * <br>
	 * Thứ tự vẽ như sau<br>
	 * 
	 * drawRear()->>>Draw listNode children--->>>DrawFront()
	 **/
	public void drawRearChild(MGGraphic g) {

	}

	/***
	 * KHÔNG NÊN OVERWRITE PHƯƠNG THỨC NÀY NẾU CHƯA NẮM RỎ MGNODE<br>
	 * Nên overWrite phương thức drawFrontChild() và drawRearChild() để sữ dụng <br>
	 * 
	 * Phương thức đệ quy vẽ node và các node con của nó
	 */
	public void paintSelfAndChild(MGGraphic g) {
		if (hide_) {
			return;
		}
		//g.setColor(1f,1f,0,0);
		//g.drawRect(contentRect);
		g.pushMatrix();
		if (transFormEnable) {
			transForm(g);
		}
		drawRearChild(g);
		// vẽ những node không phụ thuộc ZOrder node nào add trước vẽ trước
		if (children_ != null) {
			for (int i = 0, n = children_.size(); i < n; ++i) {
				try {
					MGNode child = children_.get(i);
					if (child.zOrder_ < 0) {
						child.paintSelfAndChild(g);
					} else
						break;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}

		// Vẽ có phụ thuôc tham số Zorder chỉ số nhỏ vẽ trước

		if (children_ != null) {
			for (int i = 0, n = children_.size(); i < n; ++i) {
				try {
					MGNode child = children_.get(i);
					if (child.zOrder_ >= 0) {
						child.paintSelfAndChild(g);
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}

		drawFrontChild(g);
		g.popMatrix();

	}

	public void transForm(MGGraphic g) {
		// BEGIN original implementation
		//
		// translate
		if (isRelativeAnchorPoint
				&& (anchorPointInPixels_.x != 0 || anchorPointInPixels_.y != 0))

			g.translate(-anchorPointInPixels_.x, -anchorPointInPixels_.y);

		if (anchorPointInPixels_.x != 0 || anchorPointInPixels_.y != 0) {
			g.translate(position_.x + anchorPointInPixels_.x, position_.y
					+ anchorPointInPixels_.y);
		}

		else if (position_.x != 0 || position_.y != 0) {

			g.translate(position_.x, position_.y);
		}
		// Rotale
		if (rotation_ != 0) {
			g.rotate(-rotation_, 0, 0, 1);
		}
		// Scale
		if (scaleX_ != 1.0f || scaleY_ != 1.0f) {
			g.scale(scaleX_, scaleY_, 1.0f);
		}

		// restore and re-position point
		if (anchorPointInPixels_.x != 0.0f || anchorPointInPixels_.y != 0.0f) {
			g.translate(-anchorPointInPixels_.x, -anchorPointInPixels_.y);
		}
		//
		// END original implementation
	}

	public void setTransFormEnable(boolean transFormEnable) {
		this.transFormEnable = transFormEnable;
	}

	// ****************************************
	/**
	 * Executes an action, and returns the action that is executed. The node
	 * becomes the action's target.
	 * 
	 * @warning Starting from v0.8 actions don't retain their target anymore.
	 * @return An Action pointer
	 */
	public MGAction runAction(MGAction action) {
		assert action != null : "Argument must be non-null";
		MGActionManager.sharedManager().addAction(action, this, !isRunning_);
		return action;
	}

	/** Removes all actions from the running action list */
	public void stopAllActions() {
		MGActionManager.sharedManager().removeAllActions(this);
	}

	/** Removes an action from the running action list */
	public void stopAction(MGAction action) {
		MGActionManager.sharedManager().removeAction(action);
	}

	/**
	 * Removes an action from the running action list given its tag
	 * 
	 * @since v0.7.1
	 */
	public void stopAction(int tag) {
		// assert tag != MGAction.kCCActionTagInvalid : "Invalid tag_";
		MGActionManager.sharedManager().removeAction(tag, this);
	}

	/**
	 * Gets an action from the running action list given its tag
	 * 
	 * @return the Action the with the given tag
	 */
	public MGAction getAction(int tag) {
		// assert tag != CCAction.kCCActionTagInvalid : "Invalid tag_";

		return MGActionManager.sharedManager().getAction(tag, this);
	}

	/**
	 * Returns the numbers of actions that are running plus the ones that are
	 * schedule to run (actions in actionsToAdd and actions arrays). Composable
	 * actions are counted as 1 action. Example: If you are running 1 Sequence
	 * of 7 actions, it will return 1. If you are running 7 Sequences of 2
	 * actions, it will return 7.
	 */
	public int numberOfRunningActions() {
		return MGActionManager.sharedManager().numberOfRunningActions(this);
	}

	/**
	 * schedules the "update" method. It will use the order number 0. This
	 * method will be called every frame. Scheduled methods with a lower order
	 * value will be called before the ones that have a higher order value.
	 * Only one "udpate" method could be scheduled per node.
	 * 
	 * @since v0.99.3
	 */
	public void scheduleUpdate() {
		this.scheduleUpdate(0);
	}

	/**
	 * schedules the "update" selector with a custom priority. This selector
	 * will be called every frame. Scheduled selectors with a lower priority
	 * will be called before the ones that have a higher value. Only one
	 * "update" selector could be scheduled per node (You can't have 2 'update'
	 * selectors).
	 */
	public void scheduleUpdate(int priority) {
		MGScheduler.sharedScheduler().scheduleUpdate(this, priority,
				!isRunning_);
	}

	/**
	 * unschedules the "update" method.
	 */
	public void unscheduleUpdate() {
		MGScheduler.sharedScheduler().unscheduleUpdate(this);
	}

	/**
	 * schedules a selector. The scheduled selector will be ticked every frame
	 */
	public void schedule(String selector) {
		schedule(selector, 0);
	}

	/**
	 * schedules a custom selector with an interval time in seconds. If time is
	 * 0 it will be ticked every frame. If time is 0, it is recommended to use
	 * 'scheduleUpdate' instead.
	 */
	public void schedule(String selector, float interval) {
		assert selector != null : "Argument selector must be non-null";
		assert interval >= 0 : "Argument interval must be positive";

		MGScheduler.sharedScheduler().schedule(selector, this, interval,
				!isRunning_);
	}

	/*
	 * schedules a selector. The scheduled callback will be ticked every frame.
	 * This is java way version, uses interface based callbacks. UpdateCallback
	 * in this case. It would be preffered solution. It is more polite to Java,
	 * GC, and obfuscation.
	 */
	public void schedule(UpdateCallback callback) {
		schedule(callback, 0);
	}

	/*
	 * schedules a custom callback with an interval time in seconds. If time is
	 * 0 it will be ticked every frame. If time is 0, it is recommended to use
	 * 'scheduleUpdate' instead. This is java way version, uses interface based
	 * callbacks. UpdateCallback in this case. It would be preffered solution.
	 * It is more polite to Java, GC, and obfuscation.
	 */
	public void schedule(UpdateCallback callback, float interval) {
		assert callback != null : "Argument callback must be non-null";
		assert interval >= 0 : "Argument interval must be positive";

		MGScheduler.sharedScheduler().schedule(callback, this, interval,
				!isRunning_);
	}

	/* unschedules a custom selector. */
	public void unschedule(String selector) {
		// explicit null handling
		if (selector == null)
			return;

		MGScheduler.sharedScheduler().unschedule(selector, this);
	}

	/*
	 * unschedules a custom callback. This is java way version, uses interface
	 * based callbacks. UpdateCallback in this case. It would be preffered
	 * solution. It is more polite to Java, GC, and obfuscation.
	 */
	public void unschedule(UpdateCallback callback) {
		// explicit null handling
		if (callback == null)
			return;

		MGScheduler.sharedScheduler().unschedule(callback, this);
	}

	/**
	 * unschedule all scheduled selectors: custom selectors, and the 'update'
	 * selector. Actions are not affected by this method.
	 * 
	 */
	public void unscheduleAllSelectors() {
		MGScheduler.sharedScheduler().unscheduleAllSelectors(this);
	}

	/**
	 * resumes all scheduled selectors and actions. Called internally by
	 * onEnter
	 */
	public void resumeSchedulerAndActions() {
		MGScheduler.sharedScheduler().resume(this);
		MGActionManager.sharedManager().resume(this);
	}

	/**
	 * pauses all scheduled selectors and actions. Called internally by onExit
	 */
	public void pauseSchedulerAndActions() {
		MGScheduler.sharedScheduler().pause(this);
		MGActionManager.sharedManager().pause(this);
	}

	// ********************************************

	public void onEnterTransitionDidFinish() {
		if (children_ != null)
			for (MGNode child : children_) {
				child.onEnterTransitionDidFinish();
			}
	}

	/**
	 * Stops all running actions and schedulers
	 */
	public void cleanup() {

		// actions
		stopAllActions();
		unscheduleAllSelectors();
		// timers
		if (children_ != null)
			for (int i = 0; i < children_.size(); ++i) {
				MGNode node = children_.get(i);
				node.cleanup();
			}
	}

	@Override
	public String toString() {
		return "<instance of " + this.getClass() + "| Tag = " + tag_ + ">";
	}

}
