package com.mg.studio.alice.myframework.director;

import android.graphics.PointF;

import com.mg.studio.engine.MGGraphic;

public abstract class MGScrollNode extends MGNode {

	public static final float BOUNCE_DECELERATION = 0.5f;
	public static final float NORMAL_DECELERATION = 0.92f;
	public static final float OUT_OF_FRAME_DRAG_FACTOR = 0.5f;
	public static final float MOVING_TO_DEST_ACCELERATION = 1.1f;
	public static final float MOVING_TO_DEST_MINIMUM_SPEED = 1.0f;
	public static final float PAGING_FAST_PAN = 10.0f;
	private static final long SCROLL_TIME = 40;

	static float movingToDestMinimunSpeed = MOVING_TO_DEST_MINIMUM_SPEED;
	static float pagingFastPan = PAGING_FAST_PAN;
	// size thế giới
	private float contentWidth;
	private float contentHeight;
	float vx;
	float vy;

	boolean alwaysBounceHorizontal;
	boolean alwaysBounceVertical;
	boolean pagingEnabled;

	float contentDestX;
	float contentDestY;

	protected float contentX;
	protected float contentY;
	boolean movingToDest;

	boolean scrollDelegate;

	private boolean firstDrag = true;
	private PointF lastSpeed;
	private float scrollTime;


	public MGScrollNode() {
		super();
		lastSpeed = new PointF();
		// turnOffCancelTouchOnMove();
	}

	public void setContentPos(float contentX, float contentY) {
		this.contentX = contentX;
		this.contentY = contentY;
	}

	public void setsizeW(float w, float h) {
		contentWidth = w;
		contentHeight = h;
	}

	

	
	@Override
	public PointF convertPoint(PointF parentpointF) {
		PointF point = new PointF();
		point.x = parentpointF.x + Math.abs(contentX);
		point.y = parentpointF.y + Math.abs(contentY);
		return point;
	}

/*	@Override
	public final void paintSelfAndChild(MGGraphic g) {
		if (isHide()) {
			return;
		}
		g.pushMatrix();
		
		g.translate(parent_.position_.x+position_.x + contentX , parent_.position_.y + position_.y
				+ contentY);
		if (scaleX_ != 1 || scaleY_ != 1) {
			g.scale(scaleX_, scaleY_, 1);
		}

		drawRearChild(g);
		
		// vẽ những node không phụ thuộc ZOrder node nào add trước vẽ trước
		if (children_ != null) {
			for (int i = 0; i < children_.size(); ++i) {
				MGNode child = children_.get(i);
				if (child.zOrder_ < 0) {
					child.paintSelfAndChild(g);
				} else
					break;
			}
		}

		// Vẽ có phụ thuôc tham số Zorder chỉ số nhỏ vẽ trước

		if (children_ != null) {
			for (int i = 0; i < children_.size(); ++i) {
				MGNode child = children_.get(i);
				if (child.zOrder_ >= 0) {
					child.paintSelfAndChild(g);
				}
			}
		}

		// gọi hàm vẽ thêm tùy ý
		drawFrontChild(g);
		
		
		g.popMatrix();
	}
*/
	@Override
	public void drawRearChild(MGGraphic g) {

	}

	/*
	 * public RectF getContentFrame() { return new RectF(getFrame().left +
	 * contentX, getFrame().top + contentY, getFrame().left +
	 * contentSize_.width, contentSize_.height); }
	 */

	public void mgTouchBegan(PointF point) {
		vx = 0;
		vy = 0;
	}

	public void mgTouchEnded(PointF point) {
		firstDrag = true;
		if (pagingEnabled) {
			if (contentX > 0 || contentX + contentWidth < contentSize_.width) {
				vx = lastSpeed.x * scrollTime;
				return;
			}
			if (Math.abs(lastSpeed.x) > pagingFastPan) {
				int page = (int) (-contentX / contentSize_.width);
				if (lastSpeed.x > 0) {
					// page--; // previous touch
				} else {
					page++;
				}
				if (page < 0
						|| page > (int) (contentWidth / contentSize_.width)) {
					vx = lastSpeed.x * scrollTime;
					return;
				}
				vx = lastSpeed.x * scrollTime;
				animatedSet(-(page * contentSize_.width), 0);
			} else {
				float tempX = -contentX + contentSize_.width / 2.0f;

				int page = (int) (tempX / contentSize_.width);
				if (page < 0
						|| page > (int) (contentWidth / contentSize_.width)) {
					vx = lastSpeed.x * scrollTime;
					return;
				}
				vx = lastSpeed.x * scrollTime;
				animatedSet(-(page * contentSize_.width), 0);
			}
		} else {
			vx = lastSpeed.x * scrollTime;
			vy = lastSpeed.y * scrollTime;

		}
		lastSpeed.set(0, 0);
	}

	public void mgTouchMoved(float xPre, float yPre, float xCurr, float yCurr,
			long eventDuration) {
		if (firstDrag) {
			firstDrag = false;
			lastSpeed.set(0, 0);
			scrollTime = SCROLL_TIME;
			stopScrolling();
		} else {
			scrollTime = Math.max(eventDuration + SCROLL_TIME, scrollTime);
			eventDuration = (eventDuration == 0 ? 1 : eventDuration);
			lastSpeed.set(xPre / eventDuration, yPre / eventDuration);
			if (contentWidth > contentSize_.width || alwaysBounceHorizontal) {
				float dx = xPre;
				if (contentX > 0
						|| contentX + contentWidth < contentSize_.width)
					dx *= OUT_OF_FRAME_DRAG_FACTOR;
				contentX += dx;
				handleScrolling();
			}

			if (contentHeight > contentSize_.height || alwaysBounceVertical) {
				float dy = yPre;
				if (contentY > 0
						|| contentY + contentHeight < contentSize_.height)
					dy *= OUT_OF_FRAME_DRAG_FACTOR;
				contentY += dy;
				handleScrolling();
			}
		}
	}

	public void mgTouchCancelled() {

	}

	/**
	 * xử lý khi có thao tác scroll
	 */
	public abstract void handleScrolling();

	public void update() {
		if (!isRunning()) {
			movingToDest = false;
			return;
		}
		// calculateFrameOnView();
		boolean decelerating = (vx != 0 || vy != 0);
		if (movingToDest) {
			if (vx != 0) {
				contentX += vx;
				vx *= MOVING_TO_DEST_ACCELERATION;
				if ((Math.abs(vx) < 1.0f)
						|| (vx > 0 && contentX > contentDestX)
						|| (vx < 0 && contentX < contentDestX)) {
					vx = 0;
					contentX = contentDestX;
				}
				handleScrolling();
			}
			if (vy != 0) {
				contentY += vy;
				vy *= MOVING_TO_DEST_ACCELERATION;
				if ((Math.abs(vy) < 1.0f)
						|| (vy > 0 && contentY > contentDestY)
						|| (vy < 0 && contentY < contentDestY)) {
					vy = 0;
					contentY = contentDestY;
				}
				handleScrolling();
			}
			movingToDest = (vx != 0 || vy != 0);
		} else {
			if (contentWidth <= contentSize_.width) {
				if (alwaysBounceHorizontal) {
					if (contentX != 0) {
						vx = -(contentX * BOUNCE_DECELERATION);
						if (Math.abs(vx) < 1.0f) {
							contentX = 0;
							vx = 0;
						} else {
							contentX += vx;
						}
						handleScrolling();
					}
				} else {
					vx = 0;
				}

			} else {
				if (contentX > 0) {
					vx = -(contentX * BOUNCE_DECELERATION);
					if (vx > -1.0f) {
						contentX = 0;
						vx = 0;
					} else {
						contentX += vx;
					}
					handleScrolling();
				} else if (contentX + contentWidth < contentSize_.width) {
					vx = (contentSize_.width - (contentX + contentWidth))
							* BOUNCE_DECELERATION;
					if (vx < 1.0f) {
						contentX = contentSize_.width - contentWidth;
						vx = 0;
					} else {
						contentX += vx;
					}
					handleScrolling();
				} else if (vx != 0) {
					vx *= NORMAL_DECELERATION;
					if (Math.abs(vx) < 1.0f) {
						vx = 0;
					} else {
						contentX += vx;
						handleScrolling();
					}
				}
			}

			if (contentHeight <= contentSize_.height) {
				if (alwaysBounceVertical) {
					if (contentY != 0) {
						vy = -(contentY * BOUNCE_DECELERATION);
						if (Math.abs(vy) < 1.0f) {
							contentY = 0;
							vy = 0;
						} else {
							contentY += vy;
						}
						handleScrolling();
					}
				} else
					vy = 0;
			} else {
				if (contentY > 0) {
					vy = -(contentY * BOUNCE_DECELERATION);
					if (vy > -1.0f) {
						contentY = 0;
						vy = 0;
					} else {
						contentY += vy;
					}
					handleScrolling();
				} else if (contentY + contentHeight < contentSize_.height) {
					vy = (contentSize_.height - (contentY + contentHeight))
							* BOUNCE_DECELERATION;
					if (vy < 1.0f) {
						contentY = contentSize_.height - contentHeight;
						vy = 0;
					} else {
						contentY += vy;
					}
					handleScrolling();
				} else if (vy != 0) {
					vy *= NORMAL_DECELERATION;
					if (Math.abs(vy) < 1.0f) {
						vy = 0;
					} else {
						contentY += vy;
						handleScrolling();
					}
				}
			}
		}
		if (decelerating && vx == 0 && vy == 0) {
			endDeceleratingOnScroll();
		}
		updateItSelf();
	}

	public abstract void updateItSelf();

	public abstract void endDeceleratingOnScroll();

	public final void animatedSet(float aContentX, float aContentY) {
		if (aContentX > 0 || aContentX < -(contentWidth - contentSize_.width)) {
			vx = 0;
		} else if (contentX != aContentX) {
			contentDestX = aContentX;
			if (contentDestX > contentX) {
				if (vx > 0)
					vx += movingToDestMinimunSpeed;
				else
					vx = movingToDestMinimunSpeed;
			} else {
				if (vx < 0)
					vx += -movingToDestMinimunSpeed;
				else
					vx = -movingToDestMinimunSpeed;
			}
			movingToDest = true;
		}

		if (aContentY > 0
				|| aContentY < -(contentHeight - contentSize_.height)) {
			vy = 0;
		} else if (contentY != aContentY) {
			contentDestY = aContentY;
			if (contentDestY > contentY) {
				if (vy > 0)
					vy += movingToDestMinimunSpeed;
				else
					vy = movingToDestMinimunSpeed;
			} else {
				if (vy < 0)
					vy += -movingToDestMinimunSpeed;
				else
					vy = -movingToDestMinimunSpeed;
			}
			movingToDest = true;
		}
	}

	public final void stopScrolling() {
		movingToDest = false;
		vx = vy = 0;
	}

}
