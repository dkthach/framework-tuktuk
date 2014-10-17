/*
 *	Port from SWScrollView and SWTableView for iphone 
 *	by Rodrigo Collavo on 02/03/2012
 */

package com.mg.studio.alice.myframework.scrollview;

import android.R.bool;
import android.graphics.PointF;
import android.graphics.RectF;

import com.mg.studio.alice.myframework.actions.ActionCallback;
import com.mg.studio.alice.myframework.actions.UpdateCallback;
import com.mg.studio.alice.myframework.actions.bas.MGFiniteTimeAction;
import com.mg.studio.alice.myframework.actions.instant.MGCallback;
import com.mg.studio.alice.myframework.actions.interval.MGMoveTo;
import com.mg.studio.alice.myframework.actions.interval.MGSequence;
import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.layers.MGLayer;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.alice.myframework.type.MGRect;
import com.mg.studio.alice.myframework.type.MGSize;

public class MGScrollView extends MGLayer {

	private static final float SCROLL_DEACCEL_RATE = 0.95f;
	private static final float SCROLL_DEACCEL_DIST = 1.0f;
	private static final float BOUNCE_DURATION = 0.35f;
	private static final float INSET_RATIO = 0.8f;
	public static final int ScrollViewDirectionHorizontal = 1;
	public static final int ScrollViewDirectionVertical = 2;
	public static final int ScrollViewDirectionBoth = 3;

	boolean isScrollLock;
	boolean touchMoved_;
	boolean isDragging_;
	public boolean bounces;
	private boolean clipsToBounds;
	MGClipNode container_;
	MGRect frame;

	MGPointF maxInset_;// maxInset_ và minInset_ quản lý vùng giới không cho
	MGPointF minInset_;// scroll nữa nếu tới giới hạn này hạn
	MGPointF scrollDistance_;
	MGPointF touchPoint_;
	public MGSize viewSize;

	public int direction;
	public MGScrollViewDelegate delegate;
	public bool isDragging;

	public MGScrollView(float w, float h) {
		maxInset_ = MGPointF.zero();
		minInset_ = MGPointF.zero();
		viewSize = MGSize.make(w, h);
		container_ = new MGClipNode();
		delegate = null;
		bounces = true;
		direction = ScrollViewDirectionVertical;
		container_.setContentSize(MGSize.zero());
		container_.setPosition(0, 0);
		frame = MGRect.make(0, 0, viewSize.width, viewSize.height);
		setIsTouchEnabled(true);
		setClipToBounds(true);
		addChild(container_);

	}

	/***
	 * Dùng các hằng có sẳn ScrollViewDirectionHorizontal = 1;
	 * ScrollViewDirectionVertical = 2; ScrollViewDirectionBoth = 3;
	 ***/
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public static MGScrollView view(float w, float h) {
		return new MGScrollView(w, h);
	}

	public void setClipToBounds(boolean bClip) {
		if (bClip != clipsToBounds) {
			MGRect clipRect;
			if (bClip) {
				clipRect = MGRect.make(0, 0, viewSize.width,
						viewSize.height);
			} else {
				clipRect = MGRect.make(MGClipNode.RECT_ORIGIN_INVALID, 0,
						0, 0);
			}
			clipsToBounds = bClip;
			container_.setClipRect(clipRect);
		}
	}

	RectF viewRect;

	public boolean isNodeVisible(MGNode node) {
		MGPointF offset = contentOffset();
		MGSize size = viewSize;
		float scale = 1.0f;
		viewRect = new RectF(-offset.x / scale, -offset.y / scale, -offset.x
				/ scale + size.width / scale, -offset.y / scale
				+ size.height / scale);
		return viewRect.intersect(node.getContentRect());
	}

	public void addDelegate(MGScrollViewDelegate delegate) {
		this.delegate = delegate;
	}

	public void setIsTouchEnabled(boolean e) {
		if (!e) {
			isDragging_ = false;
			touchMoved_ = false;

		}
	}

	public void setIsScrollLock(boolean e) {
		isScrollLock = e;
	}

	public void setContentOffset(MGPointF offset) {
		setContentOffset(offset, false);
	}

	public void setContentOffset(MGPointF offset, boolean animated) {
		if (animated) { // animate scrolling
			setContentOffset(offset, BOUNCE_DURATION);
		} else { // set the container position directly
			if (!bounces) {
				MGPointF minOffset = minContainerOffset();
				MGPointF maxOffset = maxContainerOffset();

				offset.x = Math.max(minOffset.x,
						Math.min(maxOffset.x, offset.x));
				offset.y = Math.max(minOffset.y,
						Math.min(maxOffset.y, offset.y));
			}
			container_.setPosition(offset);
			if (delegate != null) {
				delegate.scrollViewDidScroll(this);
			}
		}
	}

	public void setContentOffset(MGPointF offset, float dt) {
		MGFiniteTimeAction scroll, expire;
		// action trả lại khi scroll tới giớ hạn
		scroll = MGMoveTo.action(dt, offset);
		expire = MGCallback.action(new ActionCallback() {

			@Override
			public void execute(Object object) {
				stoppedAnimatedScroll();
			}
		});
		container_.runAction(MGSequence.actions(scroll, expire));

		schedule(performedAnimatedScrollCallback);
	}

	public MGPointF contentOffset() {
		return container_.getPosition();
	}

	public void setViewSize(MGSize size) {
		if (!MGSize.equalToSize(viewSize, size)) {
			viewSize = size;
			computeInsets();
			MGRect clipRect;
			if (clipsToBounds) {
				clipRect = MGRect.make(0, 0, viewSize.width,
						viewSize.height);
				container_.setClipRect(clipRect);
			}
		}
	}

	@Override
	public void setContentSize(MGSize size) {
		if (container_ == null) {
			super.setContentSize(size);
		} else {
			container_.setContentSize(size);
			computeInsets();
		}
	}
     //tính toán vùng giới hạn, còn rắc rối chổ này
	public void computeInsets() {
		maxInset_ = maxContainerOffset();
		maxInset_ = MGPointF.ccp(maxInset_.x + viewSize.width * INSET_RATIO,
				maxInset_.y + viewSize.height * INSET_RATIO);
		minInset_ = minContainerOffset();
		minInset_ = MGPointF.ccp(minInset_.x - viewSize.width * INSET_RATIO,
				minInset_.y - viewSize.height * INSET_RATIO);
	}
	public MGPointF minContainerOffset() {
		return MGPointF.ccp(0.0f, 0.0f);
	}

	public MGPointF maxContainerOffset() {
		return MGPointF.ccp(viewSize.width
				+ container_.getContentSize().width
		// * container_.getScaleX()
				, viewSize.height + container_.getContentSize().height
		// * container_.getScaleY()
				);

	}
	///kết thúc rắc rối

	public void relocateContainer(boolean animated) {
		MGPointF oldPoint, min, max;
		float newX, newY;

		min = minContainerOffset();
		max = maxContainerOffset();

		oldPoint = container_.getPosition();
		newX = oldPoint.x;
		newY = oldPoint.y;
		if (direction == ScrollViewDirectionBoth
				|| direction == ScrollViewDirectionHorizontal) {
			newX = Math.min(newX, max.x);
			newX = Math.max(newX, min.x);
		}
		if (direction == ScrollViewDirectionBoth
				|| direction == ScrollViewDirectionVertical) {
			newY = Math.min(newY, max.y);
			newY = Math.max(newY, min.y);
		}
		if (newY != oldPoint.y || newX != oldPoint.x) {
			setContentOffset(MGPointF.ccp(newX, newY), animated);
		}
	}

	

	UpdateCallback deaccelerateScrollingCallback = new UpdateCallback() {
		@Override
		public void update(float d) {
			deaccelerateScrolling(d);

		}
	};

	public void deaccelerateScrolling(float dt) {
		if (isDragging_) {
			unschedule(deaccelerateScrollingCallback);
			return;
		}

		float newX, newY;
		MGPointF maxInset, minInset;

		container_.setPosition(MGPointF.ccpAdd(container_.getPosition(),
				scrollDistance_));

		if (bounces) {
			maxInset = maxInset_;
			minInset = minInset_;
		} else {
			maxInset = maxContainerOffset();
			minInset = minContainerOffset();
		}

		// check to see if offset lies within the inset bounds
		newX = Math.min(container_.getPosition().x, maxInset.x);
		newX = Math.max(newX, minInset.x);
		newY = Math.min(container_.getPosition().y, maxInset.y);
		newY = Math.max(newY, minInset.y);

		scrollDistance_ = MGPointF.ccpSub(
				scrollDistance_,
				MGPointF.ccp(newX - container_.getPosition().x, newY
						- container_.getPosition().y));
		scrollDistance_ = MGPointF.ccpMult(scrollDistance_,
				SCROLL_DEACCEL_RATE);
		setContentOffset(MGPointF.ccp(newX, newY));

		if (MGPointF.ccpLengthSQ(scrollDistance_) <= SCROLL_DEACCEL_DIST
				* SCROLL_DEACCEL_DIST
				|| newX == maxInset.x
				|| newX == minInset.x
				|| newY == maxInset.y || newY == minInset.y) {
			unschedule(deaccelerateScrollingCallback);
			relocateContainer(true);
		}
	}

	UpdateCallback stoppedAnimatedScrollCallback = new UpdateCallback() {

		@Override
		public void update(float d) {
			stoppedAnimatedScroll();
		}
	};

	public void stoppedAnimatedScroll() {

		unschedule(performedAnimatedScrollCallback);
	}

	UpdateCallback performedAnimatedScrollCallback = new UpdateCallback() {

		@Override
		public void update(float d) {
			performedAnimatedScroll(d);

		}
	};

	public void performedAnimatedScroll(float dt) {
		if (isDragging_) {
			unschedule(performedAnimatedScrollCallback);
			;
			return;
		}
		if (delegate != null) {
			delegate.scrollViewDidScroll(this);
		}
	}

	@Override
	public MGSize getContentSize() {
		return MGSize.make(container_.getContentSize().width,
				container_.getContentSize().height);
	}

	/**
	 * make sure all children go to the container
	 * 
	 * @return
	 */
	@Override
	public MGNode addChild(MGNode node, int z, int aTag) {

		node.setAnchorPoint(0.0f, 0.0f);
		if (container_ != node) {
			container_.addChild(node, z, aTag);
		} else {
			super.addChild(node, z, aTag);
		}
		return this;
	}

	MGPointF temp = MGPointF.zero();

	@Override
	public boolean onTouchDown1(PointF points) {
		if (isHide()) {
			return false;
		}
		frame.set(0, 0, viewSize.width, viewSize.height);
		temp.set(points.x, points.y);
		if (!MGRect.containsPoint(frame, temp)) {
			touchPoint_ = MGPointF.ccp(-1.0f, -1.0f);
			isDragging_ = false;
			return false;
		}
		touchPoint_ = temp;
		touchMoved_ = false;
		isDragging_ = true; // dragging started
		if (scrollDistance_ == null) {
			scrollDistance_ = MGPointF.ccp(0.0f, 0.0f);
		} else {
			scrollDistance_.set(0, 0);
		}

		return true;
	}

	MGPointF newPoint = new MGPointF();

	@Override
	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr, long eventDuration) {
		if (isHide() || isScrollLock) {
			return false;
		}

		touchMoved_ = true;

		if (isDragging_) {
			MGPointF moveDistance;
			float newX, newY;
			newPoint.set(xCurr, yCurr);

			moveDistance = MGPointF.ccpSub(newPoint, touchPoint_);
			touchPoint_.set(newPoint);
			frame.set(0, 0, viewSize.width, viewSize.height);
			if (MGRect.containsPoint(frame, newPoint)) {
				switch (direction) {
				case ScrollViewDirectionVertical:
				moveDistance = MGPointF.ccp(0.0f, moveDistance.y);
					break;
				case ScrollViewDirectionHorizontal:
				moveDistance = MGPointF.ccp(moveDistance.x, 0.0f);
					break;
				default:
					break;
				}
				container_.setPosition(MGPointF.ccpAdd(
						container_.getPosition(), moveDistance));

				// check to see if offset lies within the inset bounds
				newX = Math.min(container_.getPosition().x, maxInset_.x);
				newX = Math.max(newX, minInset_.x);
				newY = Math.min(container_.getPosition().y, maxInset_.y);
				newY = Math.max(newY, minInset_.y);

				scrollDistance_ = MGPointF.ccpSub(moveDistance, MGPointF
						.ccp(newX - container_.getPosition().x, newY
								- container_.getPosition().y));
				setContentOffset(MGPointF.ccp(newX, newY));
			}

			return true;
		}

		return true;
	}

	@Override
	public boolean onTouchUp1(PointF pointF) {
		if (isHide()) {
			return false;
		}

		if (touchMoved_) {
			schedule(deaccelerateScrollingCallback);
		}

		isDragging_ = false;
		touchMoved_ = false;

		return true;
	}

}
