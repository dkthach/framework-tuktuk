package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;

/**
 * @author Dk Thach
 * 
 *         MGTransitionEaseScreen để chạy các Ease action (action làm dịu)
 */

public interface MGTransitionEaseScreen {
	/**
	 * screen implements va hien thuc lai ham nay trả về 1 ease action bất kỳ
	 */
	public MGIntervalAction easeAction(MGIntervalAction action);
}
