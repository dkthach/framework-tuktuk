/**
 * 
 */
package com.mg.studio.alice.myframework.director;

import android.graphics.PointF;

/**
 * @author Dk Thach
 * 
 */
public interface MGDiaLogGLInterface {
	public boolean onBackPressed() ;
	public void dismiss();
	public void destroy();
	public void cancel();

	interface OnTouchListener {
		public boolean onTouchBegan(PointF p);

		public boolean onTouchEnd(PointF p);

		public boolean onTouchMove(float p1, float p2, float p3, float p4);
	}

	interface OnCancelListener {

		public void onCancel(MGDiaLogGLInterface dialog);
	}

	interface OnDismissListener {

		public void onDismiss(MGDiaLogGLInterface dialog);
	}

	interface OnShowListener {

		public void onShow(MGDiaLogGLInterface dialog);
	}
	
	interface OnDestroyListener {

		public void onDesTroy(MGDiaLogGLInterface dialog);
	}

	interface OnClickListener {
		public boolean click(MGDiaLogGLInterface dialog, PointF p, int id);
	}
	
	
	

}
