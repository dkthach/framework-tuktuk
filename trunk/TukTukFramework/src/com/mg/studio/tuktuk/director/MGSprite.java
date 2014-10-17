package com.mg.studio.tuktuk.director;

import android.graphics.RectF;

import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.tuktuk.actions.UpdateCallback;

/**
 * @author Dk Thach
 */

public class MGSprite extends MGNode implements UpdateCallback {
	private MGImage image_sheets;
	private int frameNumber = 1, index = 0, frameToDraw , numRow, numCol,
			frameDrawROW, frameDrawCOl;
	private int[] listFrameIndexToRunAnimation;// quản lý thứ tự vẽ frame
	private RectF rectCutFrame;
	private boolean isGridSheet;
	private int countPulse, interval;
	private boolean Onlyframe;
	private boolean repeatForever =true; // lặp vô tận hay không
	private int nLoop = 1; // lặp bao nhiêu lần
	private boolean finishLoop;
	private boolean flagSetlistIndex = true;

	private void init(MGImage sheetImg, int nCol, int nRow,
			int[] listFrameIndexToRunAnimation, boolean isGridSheet) {
		this.isGridSheet = isGridSheet;
		rectCutFrame = new RectF();
		numCol = nCol;
		numRow = nRow;
		frameToDraw = 0;
		this.frameNumber = nCol * nRow;
		if (listFrameIndexToRunAnimation == null) {
			this.listFrameIndexToRunAnimation = new int[frameNumber];
			for (int i = 0; i < this.listFrameIndexToRunAnimation.length; i++) {
				this.listFrameIndexToRunAnimation[i] = i;
			}
		} else {
			this.listFrameIndexToRunAnimation = listFrameIndexToRunAnimation;
		}
		image_sheets = sheetImg;
		updateContentSize();
		schedule(this);
	}

	public void setImageSheet(MGImage imageSheet) {
		finishLoop = false;
		this.image_sheets = imageSheet;
		updateContentSize();
	}
	private void updateContentSize(){
		if (isGridSheet) {
			contentSize_.set((float)image_sheets.getWidthContent() / (float)numCol,
					(float)image_sheets.getHeightContent() / (float)numRow);
		} else {
			contentSize_.set((float)image_sheets.getWidthContent() / (float)frameNumber,
					(float)image_sheets.getHeightContent());
		}

	}

	/***
	 * nếu giá trị là False thì số lần lặp phụ thuộc nLoop (gọi hàm setnLoop(int
	 * nLoop)) nếu giá trị là true thì lặp mãi mãi
	 **/
	public void setRepeatForever(boolean repeatForever) {
		if(repeatForever){
			finishLoop=false;
		}
		this.repeatForever = repeatForever;
	}

	/** lặp lại bao nhiêu lần thì dừng và gọi hàm sự kiện spriteFinishLoop() ***/
	public void setnLoop(int nLoop) {
		this.nLoop = nLoop;
	}

	public boolean isFinishLoop() {
		return finishLoop;
	}
	public int getcurrentFrameDraw() {
		return frameToDraw;
	}

	

	public MGSprite() {
		image_sheets = new MGImage();
		this.listFrameIndexToRunAnimation = new int[0];

	}

	/***
	 * @param imageSheet_Row
	 *            sheet MGimage được sắp xếp theo dòng
	 * @param frameNumber
	 *            Số frame của sheet image
	 * @param listFrameIndexToRunAnimation
	 *            Mảng quản lý thứ tự chạy vẽ các frame trong sheet
	 *
	 */
	public  MGSprite(MGImage imageSheet_Row,
			int frameNumber, int[] listFrameIndexToRunAnimation) {
		 init(imageSheet_Row, frameNumber, 1,
				listFrameIndexToRunAnimation, false);

	}

	/***
	 * Frame sẽ chạy tự động từ đầu đến cuối Sheet image
	 * 
	 * @param imageSheet_Row
	 *            sheet MGimage được sắp xếp theo dòng
	 * @param frameNumber
	 *            Số frame của sheet image
	 * 
	 * 
	 */
	public  MGSprite(MGImage imageSheet_Row, int frameNumber) {
		init(imageSheet_Row, frameNumber, 1, null, false);
	}

	/**
	 * @param imageSheet_Grid
	 *            sheetimage nhiều dòng nhiều cột
	 * @param nCol
	 *            số cột
	 * @param nRow
	 *            số dòng
	 *  <br>
	 *         --- Gọi setInterval(thời gian mili giây) animation sẽ chạy<br>
	 *         Frame sẽ chạy tự động từ đầu đến cuối Sheet image Nếu ko <br>
	 *         setListFrameIndexToRunAnimation(truyền vào mảng index cụ thể)
	 */
	public MGSprite(MGImage imageSheet_Grid, int nCol,
			int nRow) {
		init(imageSheet_Grid, nCol, nRow, null, true);
	}


	public void update(float dt) {
		if (isRunning()) {
			updateIndexSheet();
			if (isGridSheet)
				caculatorFramDraw(frameToDraw); // Thiết lập Rectagle để cắt
												// frame
		}
	}

	private void updateIndexSheet() {
		if (Onlyframe == false && !finishLoop) {
			countPulse++;

			if (countPulse >= interval) {
				index++;
				if (index == listFrameIndexToRunAnimation.length) {
					index = 0;
					if (!repeatForever) {
						nLoop--;
						if (nLoop == 0) {
							finishLoop = true;
						}
					}
				}
				countPulse = 0;
			}
			frameToDraw = listFrameIndexToRunAnimation[index];
		}

	}

	// tính toán như thế này để khi sữ dụng chỉ cần nhập vào mảng 1 dãy số
	// tùy ý sẽ tính ra dc colum va row
	private void caculatorFramDraw(int frameDraw) {
		int temb = frameDraw % numCol;
		if (temb == 0) {
			frameDrawCOl = 0;
		} else {
			frameDrawCOl = temb > 0 ? temb : numCol - 1;
		}
		frameDrawROW = frameDraw / numCol;

	}

	/***
	 * cho phép ngưng chạy frame vẽ 1 frame cố định theo ý muốn trong sprite
	 * sheet
	 **/
	public void setOnlyFrame(int frameIndex, boolean onLyframe) {
		this.frameToDraw = frameIndex;

		Onlyframe = onLyframe;
		
	}

	public void draw(MGGraphic g, float x, float y, int graphicFlip) {
		if (isGridSheet)
			drawSheetGRID(g, x, y, 0, 0, 0, graphicFlip);
		else
			drawSprROW(g, x, y, 0, 0, 0);
	}

	public void draw(MGGraphic g, float x, float y, float angle) {
		if (isGridSheet)
			drawSheetGRID(g, x, y, 0, 0, angle, g.FLIP_OFF);
		else
			drawSprROW(g, x, y, 0, 0, angle);
	}

	public void draw(MGGraphic g, float x, float y, float newWidth,
			float newHeight) {
		if (isGridSheet)
			drawSheetGRID(g, x, y, newWidth, newHeight, 0, g.FLIP_OFF);
		else
			drawSprROW(g, x, y, newWidth, newHeight, 0);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param newWidth
	 *            chiều rộng vẽ ra màn hình
	 * @param newHeight
	 *            chiều cao vẽ ra màn hình
	 * @param angle
	 *            0-360
	 */
	public void draw(MGGraphic g, float x, float y, float newWidth,
			float newHeight, float angle, int mgimageFlip) {
		if (isGridSheet)
			drawSheetGRID(g, x, y, newWidth, newHeight, angle, mgimageFlip);
		else
			drawSprROW(g, x, y, newWidth, newHeight, angle);
	}

	/*** Vẽ frame sprite **/
	private void drawSprROW(MGGraphic g, float x, float y, float newWidth,
			float newHeight, float angle) {
		position_.x = x;
		position_.y = y;
		// nếu hình chỉ 1 frame thì vẽ đơn giản không cần cắt frame
		if (frameNumber == 1) {
			g.drawImage(image_sheets, x, y, newWidth, newHeight, angle);
		} else {

			rectCutFrame.set((contentSize_.width * frameToDraw), 0,
					(contentSize_.width * frameToDraw) + contentSize_.width,
					contentSize_.height);
			g.drawImage(image_sheets, x, y, newWidth, newHeight,
					rectCutFrame, angle);

		}
	}

	/** vẽ frame trong sheet Image có nhiều ROW nhiều COLUM **/

	private void drawSheetGRID(MGGraphic g, float x, float y, float newWidth,
			float newHeight, float angle, int flip) {
		position_.x = x;
		position_.y = y;
		rectCutFrame.set(contentSize_.width * frameDrawCOl, contentSize_.height
				* frameDrawROW, (contentSize_.width * frameDrawCOl)
				+ contentSize_.width, (contentSize_.height * frameDrawROW)
				+ contentSize_.height);

		g.drawImage(image_sheets, x, y, newWidth, newHeight, rectCutFrame,
				flip, angle);

	}

	/**
	 * @return true false (Sprite đã khởi tạo xong hay chưa)
	 */
	public boolean isReady() {
		return this.image_sheets.isReady();

	}

	public void setListFrameIndexToRunAnimation(
			int[] listFrameIndexToRunAnimation) {
		index = 0;
		this.listFrameIndexToRunAnimation = listFrameIndexToRunAnimation;
	}

	public void setInterval(int interval_Timemilis) {
		this.interval = (int) (interval_Timemilis / (1000 / 40));

	}

}
