/*
 *	Port from SWScrollView and SWTableView for iphone 
 *	by Rodrigo Collavo on 02/03/2012
 */

package com.mg.studio.alice.myframework.scrollview;

import com.mg.studio.alice.myframework.type.MGSize;


/**
 * Data source that governs table backend data.
 */
public interface MGTableViewDataSource {
        /**
         * cell height for a given table.
         *
         * @param table table to hold the instances of Class
         * @return cell size
         */
        public MGSize cellSizeForTable(MGTableView table);
        /**
         * a cell instance at a given index
         *
         * @param idx index to search for a cell
         * @return cell found at idx
         */
        public MGTableViewCell tableCellAtIndex(MGTableView table, int idx);
        /**
         * Returns number of cells in a given table view.
         *
         * @return number of cells
         */
        public int numberOfCellsInTableView(MGTableView table);
}
