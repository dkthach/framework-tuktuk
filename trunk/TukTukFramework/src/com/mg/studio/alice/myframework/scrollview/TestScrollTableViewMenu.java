package com.mg.studio.alice.myframework.scrollview;

import java.util.ArrayList;

import android.graphics.PointF;

import com.mg.studio.alice.myframework.director.CanvasGame;
import com.mg.studio.alice.myframework.director.MGScreen;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.alice.myframework.type.MGRect;
import com.mg.studio.alice.myframework.type.MGSize;

public class TestScrollTableViewMenu extends MGScreen implements
		MGTableViewDelegate, MGTableViewDataSource {
	public static MGScreen screen() {

		TestScrollTableViewMenu gameLayer = new TestScrollTableViewMenu(
				new ArrayList<String>());
		return gameLayer;
	}

	private MGSize cellSize_;

	private MGTableView tableView_;
	private ArrayList<String> elements_;

	// You need to send an array of sprite names, check on commented code in
	// ScrollView example
	public TestScrollTableViewMenu(ArrayList<String> array) {
		cellSize_ = MGSize.make(77, 78);
		// setIsTouchEnabled(true);
		elements_ = array;

		// CCLayerColor *clipping = CCLayerColor::layerWithColor(ccc4(255,
		// 255, 255, 255));
		// clipping.setPosition(ccp(50, 100));
		// clipping.setContentSize(MGSizeMake(100, 300));
		//
		// addChild(clipping);

		MGSize winSize = CanvasGame.sizeDevices;
		tableView_ = MGTableView.view(this, MGSize.make(77, 300));// winSize.width,
														// 57));
		tableView_.tDelegate = this;
		tableView_.dataSource = this;
		// tableView_.setClipsToBounds(true);
		// tableView_.setViewSize(MGSizeMake(100, 300));
		tableView_.setPosition(MGPointF.ccp(50, 100));

		// tableView_.setDirection(SWScrollViewDirectionHorizontal);
		tableView_.setVerticalFillOrder(MGTableView.TableViewFillTopDown);

		addChild(tableView_);

		// CCSprite *image = CCSprite::spriteWithFile("Icon.png");
		// tableView_.addChild(image);
		tableView_.reloadData();
	}

	public void setPosition(MGPointF position) {
		tableView_.setPosition(position);
	}

	public MGPointF getPosition() {
		return tableView_.getPosition();
	}

	public static TestScrollTableViewMenu menu(ArrayList<String> array) {
		return new TestScrollTableViewMenu(array);
	}

	public boolean containsTouchLocation(MGPointF pos) {
		MGSize s = tableView_.viewSize;
		MGRect r = MGRect.make(getPosition().x, getPosition().y, s.width,
				s.height);
		return MGRect.containsPoint(r, pos);
	}
	MGPointF p ;
	@Override
	public boolean onTouchDown1(PointF points) {
		tableView_.onTouchDown1(points);
	 p  = MGPointF.make(points.x, points.y);
		if (!containsTouchLocation(p))
			return false;
		return true;
	}
	@Override
	public boolean onTouchUp1(PointF pointF) {
		tableView_.onTouchUp1(pointF);
		return true;
	}
	@Override
	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr, long eventDuration) {
		tableView_.onTouchDrag1(xPre, yPre, xCurr, yCurr, eventDuration);
		return true;
	}

	// SWTableViewDelegate Protocol
	public void tableCellTouched(MGTableView table, MGTableViewCell cell) {
		// setPosition(ccp(getPosition().x, getPosition().y +
		// cellSize_.height));
	}

	// SWTableViewDataSource Protocol
	public MGSize cellSizeForTable(MGTableView table) {
		return cellSize_;
	}

	public MGTableViewCell tableCellAtIndex(MGTableView table, int idx) {
		MGTableViewSpriteCell cell = (MGTableViewSpriteCell) table
				.dequeueCell();
		if (cell == null) {
			cell = new MGTableViewSpriteCell();
		}

		String name = (String) elements_.get(idx);
		// CCSprite image = CCSprite.sprite(name);
		SpriteNode image = SpriteNode.create(RM.dialogBg);
		// image.setColor(ccColor3B.ccc3(255/(idx + 1), 255/(idx + 1), 255));
		cell.setSprite(image);
		cell.getSprite()
				.setPosition(
						MGPointF.ccp((cellSize_.width - image
								.getContentSize().width) / 2,
								(cellSize_.height - image
										.getContentSize().height) / 2));
		return cell;
	}

	public int numberOfCellsInTableView(MGTableView table) {
		return elements_.size();
	}
}
