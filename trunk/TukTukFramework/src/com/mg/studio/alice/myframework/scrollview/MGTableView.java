/*
 *	Port from SWScrollView and SWTableView for iphone 
 *	by Rodrigo Collavo on 02/03/2012
 */

package com.mg.studio.alice.myframework.scrollview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import android.graphics.PointF;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.alice.myframework.type.MGSize;

public class MGTableView extends MGScrollView implements MGScrollViewDelegate{

    public static final int TableViewFillTopDown = 1;
    public static final int TableViewFillBottomUp = 2;

    /**
     * vertical direction of cell filling
     */
    int      m_vordering;
    /**
     * index set to query the indexes of the cells used.
     */
    //TODO: use C++ object
    //NSMutableIndexSet* indices_;
    HashSet<Integer>  m_indices;
//    CCMutableArray<int*> *m_indices;
    /**
     * cells that are currently in the table
     */
    ArrayList<MGTableViewCell> m_cellsUsed;
    /**
     * free list of cells
     */
    ArrayList<MGTableViewCell> m_cellsFreed;
    /**
     * weak link to the delegate object
     */
    //SWTableViewDelegate*   tDelegate_;

    /**
     * data source
     */
	public MGTableViewDataSource dataSource;
    /**
     * delegate
     */
    //TODO: fix this delegate declaration
    public MGTableViewDelegate tDelegate;
    /**
     * determines how cell is ordered and filled in the view.
     */
    public int verticalFillOrder;
    
    public static MGTableView view(MGTableViewDataSource dataSource, MGSize size)
    {
        return view(dataSource, size, null);
    }
    
    public static MGTableView view(MGTableViewDataSource dataSource, MGSize size, MGNode container)
    {
        MGTableView table;
        table = new MGTableView(size, container);
        table.dataSource = dataSource;
        table._updateContentSize();
        
        return table;
    }
    
    public MGTableView(MGSize size, MGNode container)
    {
    	super(size.width,size.height);
    	
        m_cellsUsed         = new ArrayList<MGTableViewCell>();
        m_cellsFreed        = new ArrayList<MGTableViewCell>();

        m_indices           = new HashSet<Integer>();
        tDelegate           = null;
        m_vordering         = TableViewFillBottomUp;
        super.direction		= ScrollViewDirectionVertical;
        
        super.delegate = this;
    }
    
    public void setVerticalFillOrder(int fillOrder)
    {
        if (m_vordering != fillOrder) {
            m_vordering = fillOrder;
            if(m_vordering == TableViewFillTopDown){
            	//container_.setPosition(MGPointF.ccp(container_.getPosition().x, -container_.getContentSize().height+viewSize.height));
                //This is necessary for scrolling physic
                //setPosition(container_.getPosition());
            }
            
            if (m_cellsUsed.size() > 0) {
                reloadData();
            }
        }
    }
    
    public void reloadData()
    {
        MGTableViewCell cell;
        for (int i=0;i < m_cellsUsed.size(); i++) {
            cell = m_cellsUsed.get(i);
            m_cellsFreed.add(cell);
            cell.setObjectID(m_cellsFreed.indexOf(cell));
            cell.reset();
            if (cell.getParent() == container_) {
                container_.removeChild(cell, true);
            }
        }
        
        m_indices.clear();
        m_cellsUsed = new ArrayList<MGTableViewCell>();
        
        _updateContentSize();
        if (dataSource.numberOfCellsInTableView(this) > 0) {
            scrollViewDidScroll(this);
        }
    }
    
    private MGTableViewCell cellAtIndex(int idx)
    {
        return _cellWithIndex(idx);
    }
    
    private void updateCellAtIndex(int idx)
    {
        if (idx == Integer.MAX_VALUE || idx > dataSource.numberOfCellsInTableView(this)-1) {
            return;
        }
        
        MGTableViewCell cell;
        
        cell = _cellWithIndex(idx);
        if (cell != null) {
            _moveCellOutOfSight(cell);
        }
        cell = dataSource.tableCellAtIndex(this, idx);
        _setIndex(idx, cell);
        _addCellIfNecessary(cell);
    }

    private void insertCellAtIndex(int idx)
    {
        //This function is not used for the momment
        /*
        if (idx == LONG_MAX || idx > dataSource.numberOfCellsInTableView(this)-1) {
            return;
        }
        
        CCTableViewCell     *cell;
        int                 newIdx;
        
        cell        = m_cellsUsed.get(idx);
        if (cell) {
            newIdx = m_cellsUsed.getIndexOfObject(cell);
            for (int i=newIdx; i<m_cellsUsed.size(); i++) {
                cell = m_cellsUsed.get(i);
                _setIndex(cell.getObjectID()+1, cell);
            }
        }
        
        //TODO: ??
        //indices_.shiftIndexesStartingAtIndex(idx, 1);
        m_indices.insert(m_indices.end(), m_indices.size());
        
        //insert a new cell
        cell = dataSource.tableCellAtIndex(this, idx);
        _setIndex(idx, cell);
        _addCellIfNecessary(cell);
        
        _updateContentSize();
         */
    }

    private void removeCellAtIndex(int idx)
    {
        //This function is not used for the momment
        /*
        if (idx == LONG_MAX || idx > dataSource.numberOfCellsInTableView(this)-1) {
            return;
        }
        
        CCTableViewCell   *cell;
        int         	  newIdx;
        
        cell = _cellWithIndex(idx);
        if (!cell) {
            return;
        }
        
        newIdx = m_cellsUsed.getIndexOfObject(cell);
        
        //remove first
        _moveCellOutOfSight(cell);
        
        m_indices.erase(idx+1);
        for (int i=m_cellsUsed.size()-1; i > newIdx; i--) {
            cell = m_cellsUsed.get(i);
            _setIndex(cell.getObjectID()-1, cell);
        }
         */
    }

    public MGTableViewCell dequeueCell()
    {
        MGTableViewCell cell;
        
        if (m_cellsFreed.size() == 0) {
            return null;
        } else {
            cell = (MGTableViewCell)m_cellsFreed.get(0);
            m_cellsFreed.remove(0);
        }
        return cell;
    }
    
    private void _addCellIfNecessary(MGTableViewCell cell)
    {
        if (cell.getParent() != container_) {
            container_.addChild(cell);
        }
        
        //Inserting the new cell on the proper place (sorted by indexes)
        boolean inserted = false;
        for (int i = 0; i < m_cellsUsed.size(); i++) {
            if(m_cellsUsed.get(i).getObjectID() > cell.getObjectID()){
                m_cellsUsed.add(i, cell);
                inserted = true;
                break;
            }
        }
        if(!inserted) m_cellsUsed.add(cell);
        
        m_indices.add(cell.getObjectID());
    }
    
    void _updateContentSize()
    {
        MGSize     size, cellSize;
        int		   cellCount;
        
        cellSize  = dataSource.cellSizeForTable(this);
        cellCount = dataSource.numberOfCellsInTableView(this);
        
        switch (super.direction) {
            case ScrollViewDirectionHorizontal:
                size = MGSize.make(cellCount * cellSize.width, cellSize.height);
                break;
            default:
                size = MGSize.make(cellSize.width, cellCount * cellSize.height);
                break;
        }
        setContentSize(size);
    }

    private MGPointF _offsetFromIndex(int index)
    {
        MGPointF offset = __offsetFromIndex(index);
        
        MGSize cellSize = dataSource.cellSizeForTable(this);
        if (m_vordering == TableViewFillTopDown) {
            offset.y = container_.getContentSize().height - offset.y - cellSize.height;
        }
        return offset;
    }
    
    private MGPointF __offsetFromIndex(int index)
    {
        MGPointF offset;
        MGSize  cellSize;
        
        cellSize = dataSource.cellSizeForTable(this);
        switch (super.direction) {
            case ScrollViewDirectionHorizontal:
                offset = MGPointF.ccp(cellSize.width * index, 0.0f);
                break;
            default:
                offset = MGPointF.ccp(0.0f, cellSize.height * index);
                break;
        }
        
        return offset;
    }
    
    private int _indexFromOffset(MGPointF offset)
    {
        int index;
        int maxIdx = dataSource.numberOfCellsInTableView(this)-1;
        
        MGPointF newOffset = MGPointF.make(offset.x, offset.y);
        MGSize cellSize = dataSource.cellSizeForTable(this);
        if (m_vordering == TableViewFillTopDown) {
            newOffset.y = container_.getContentSize().height - offset.y - cellSize.height;
        }
        index = Math.max(0, __indexFromOffset(newOffset));
        index = Math.min(index, maxIdx);
        return index;
    }
    
    private int __indexFromOffset(MGPointF offset)
    {
        int  	   index;
        MGSize     cellSize;
        
        cellSize = dataSource.cellSizeForTable(this);
        
        switch (super.direction) {
            case ScrollViewDirectionHorizontal:
                index = (int) (offset.x/cellSize.width);
                break;
            default:
                index = (int) (offset.y/cellSize.height);
                break;
        }
        
        return index;
    }
    
    private MGTableViewCell _cellWithIndex(int cellIndex)
    {
        MGTableViewCell found;
        
        found = null;
        
        Iterator<Integer> i = m_indices.iterator();
        while(i.hasNext()){
        	if(cellIndex == i.next()){

                for (int index = 0; index < m_cellsUsed.size(); index++) {
                    if(m_cellsUsed.get(index).getObjectID() == cellIndex){
                        found = (MGTableViewCell)m_cellsUsed.get(index);
                        break;
                    }
                }
                break;
        	}
        }
        
        return found;
    }
    
    private void _moveCellOutOfSight(MGTableViewCell cell)
    {
        m_cellsFreed.add(cell);
        m_cellsUsed.remove(cell);

        Integer number;
        Iterator<Integer> it = m_indices.iterator();
        while(it.hasNext()){
        	number = it.next();
        	if(cell.getObjectID() == number){
        		m_indices.remove(number);
        		break;
        	}
        }
        
        cell.reset();
        if (cell.getParent() == container_) {
            container_.removeChild(cell, false);
        }
    }
    
    private void _setIndex(int index, MGTableViewCell cell)
    {
        cell.setAnchorPoint(MGPointF.ccp(0.0f, 0.0f));
        cell.setPosition(_offsetFromIndex(index));
        cell.setObjectID(index);
    }
    
    public void scrollViewDidScroll(MGScrollView view)
    {
        int		          startIdx, endIdx, idx, maxIdx;
        MGPointF           offset;
        
        maxIdx = dataSource.numberOfCellsInTableView(this);
        
        if (maxIdx == 0) {
            return; // early termination
        }
        
        offset   = MGPointF.ccpMult(contentOffset(), -1);
        maxIdx   = Math.max(maxIdx - 1, 0);
        
        MGSize cellSize = dataSource.cellSizeForTable(this);
        
        if (m_vordering == TableViewFillTopDown) {
            offset.y = offset.y + (viewSize.height/container_.getScaleY()) - cellSize.height;
        }
        startIdx = _indexFromOffset(offset);
        if (m_vordering == TableViewFillTopDown) {
            offset.y -= viewSize.height / container_.getScaleY();
        } else {
            offset.y += viewSize.height/container_.getScaleY();
        }
        offset.x += viewSize.width/container_.getScaleX();
            
        endIdx   = _indexFromOffset(offset);

        //Removing cells out of sight starting from the top or left until find the first visible cell
        if (m_cellsUsed.size() > 0) {
            idx = m_cellsUsed.get(0).getObjectID();
            while(idx <startIdx) {
                MGTableViewCell cell = m_cellsUsed.get(0);
                _moveCellOutOfSight(cell);
                if (m_cellsUsed.size() > 0) {
                    idx = m_cellsUsed.get(0).getObjectID();
                } else {
                    break;
                }
            }
        }
        
        //Removing cells out of sight starting from the bottom or right until find the last visible cell
        if (m_cellsUsed.size() > 0) {
            idx = m_cellsUsed.get(m_cellsUsed.size()-1).getObjectID();
            while(idx <= maxIdx && idx > endIdx) {
                MGTableViewCell cell = m_cellsUsed.get(m_cellsUsed.size()-1);
                _moveCellOutOfSight(cell);
                if (m_cellsUsed.size() > 0) {
                    idx = m_cellsUsed.get(m_cellsUsed.size()-1).getObjectID();
                } else {
                    break;
                }
            }
        }
        
        for (int i=startIdx; i <= endIdx; i++) {
            //Checking if there are pending cell to show on screen
            boolean canUpdate = true;
            Iterator<Integer> it = m_indices.iterator();
            while(it.hasNext()){
            	if(i == it.next()){
            		canUpdate = false;
            		break;
            	}
            }
            
            //updating cell for showing on screen
            if(canUpdate)updateCellAtIndex(i);
        }
    }
    
    @Override
	public boolean onTouchUp1(PointF pointF) {
	    if (isHide()) {
	            return false;
	        }
	        
	      //  if (touches_.size() == 1 && !touchMoved_) {
	        if (!touchMoved_) {
	            int		          index;
	            MGTableViewCell   cell;
	           // MGPointF           pointF;
	            
	           // point = container_.convertTouchToNodeSpace(event);
	            if (m_vordering == TableViewFillTopDown) {
	                MGSize cellSize = dataSource.cellSizeForTable(this);
	                pointF.y -= cellSize.height;
	            }
	            index = _indexFromOffset((MGPointF)pointF);
	            cell  = _cellWithIndex(index);
	            
	            if (cell != null) {
	                tDelegate.tableCellTouched(this, cell);
	            }
	        }
	        
	     
		return super.onTouchUp1(pointF);
	}

	

	@Override
	public void scrollViewDidZoom(MGScrollView view) {
		// TODO Auto-generated method stub
		
	}
}
