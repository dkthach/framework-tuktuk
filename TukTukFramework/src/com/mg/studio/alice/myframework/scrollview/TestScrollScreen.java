/**
 * 
 */
package com.mg.studio.alice.myframework.scrollview;

import android.graphics.PointF;

import com.mg.studio.alice.CanvasGame;
import com.mg.studio.alice.game.resoucredata.RM;
import com.mg.studio.alice.myframework.director.MGScreen;
import com.mg.studio.alice.myframework.type.MGSize;

/**
 * @author Dk Thach
 *
 */
public class TestScrollScreen extends MGScreen {
	public static MGScreen screen() {
		TestScrollScreen gameLayer = new TestScrollScreen();
		return gameLayer;
	}
	MGScrollView scrollView;
	OneMapImageNode map1,map2,map3,map4; 

	public TestScrollScreen() {
		//khoi tao imageNode Map
		map1 = new OneMapImageNode(RM.map1,"map 1");
		map2 = new OneMapImageNode(RM.map2,"map 2");
		map3 = new OneMapImageNode(RM.map3,"map 3");
		map4 = new OneMapImageNode(RM.map4,"map 4");
		float heightMap = map1.getContentSize().height;
		map1.setPosition(0, -heightMap*4);
		map2.setPosition(0, map1.getPosition().y+heightMap);
		map3.setPosition(0, map2.getPosition().y+heightMap);
		map4.setPosition(0, map3.getPosition().y+heightMap);
		//-------
		
		MGSize winSize = CanvasGame.sizeDevices;
		scrollView = MGScrollView.view(CanvasGame.widthDevices - 30 ,
				CanvasGame.heightDevices-50);
		scrollView.setContentSize(MGSize.make(RM.WIDTH_GAME*RM.rate, heightMap*2));
		//set true để có độ nẩy
		scrollView.bounces = true;
		scrollView.addChild(map1);
		scrollView.addChild(map2);
		scrollView.addChild(map3);
		scrollView.addChild(map4);
		/*scrollView.setViewSize(MGSize.make(CanvasGame.widthDevices - 30 ,
				CanvasGame.heightDevices-50));*/
		scrollView.setPosition(10,10);
		addChild(scrollView, 10);
		
		
		scrollView.addDelegate(new MGScrollViewDelegate() {
			
			@Override
			public void scrollViewDidZoom(MGScrollView view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void scrollViewDidScroll(MGScrollView view) {
				
				
				
			}
		});
	}

	@Override
	public boolean onTouchDown1(PointF points) {
		scrollView.onTouchDown1(points);
		return true;
	}

	@Override
	public boolean onTouchDrag1(float xPre, float yPre, float xCurr,
			float yCurr, long eventDuration) {
		scrollView.onTouchDrag1(xPre, yPre, xCurr, yCurr, eventDuration);
		return true;
	}
	

	@Override
	public boolean onTouchUp1(PointF pointF) {
		scrollView.onTouchUp1(pointF);
		return true;
	}

}
