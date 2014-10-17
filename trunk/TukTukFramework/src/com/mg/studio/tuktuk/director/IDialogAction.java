package com.mg.studio.tuktuk.director;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.type.MGPointF;

/**
 * @author Dk Thach
 * 
 * 
 */

public interface IDialogAction {
	/** hien thuc 1 ease action cho dialog **/
	public MGIntervalAction easeActionForShow(MGIntervalAction action);

	/** action xuat hien lúc khởi tạo dialog **/
	public MGIntervalAction showAction(MGPointF pointF);

	/**action bien mat cho dialog **/
	public MGIntervalAction dismissAction(MGPointF pointF);
	
	public MGIntervalAction easeActionForDismiss(MGIntervalAction action);
	/**action tạm ẩn  khi push thêm vào dialog stack**/
	public MGIntervalAction hideAction() ;
	/**action show trở lại khi pop dialog stack**/
	public MGIntervalAction popShowAction();

}
