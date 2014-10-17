/**
 * 
 */
package com.mg.studio.alice.myframework.type;

/**
 * @author Dk Thach
 * 
 */
public class MGLocation {
	public int row;
	public int colum;

	public MGLocation() {
	}

	public MGLocation(int row, int colum) {
		this.row = row;
		this.colum = colum;
	}

	public void setColum(int colum) {
		this.colum = colum;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColum() {
		return colum;
	}

	public int getRow() {
		return row;
	}

	public void setLocation(MGLocation newloc) {
		setLocation(newloc.row, newloc.colum);
	}

	public void setLocation(int row, int colum) {
		setColum(colum);
		setRow(row);
	}

	public boolean equal(MGLocation loc) {
		if (loc.row == this.row && loc.colum == this.colum) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "row:<" + row + "> colum:<" + colum + ">";
	}

}
