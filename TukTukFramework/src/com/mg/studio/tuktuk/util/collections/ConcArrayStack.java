package com.mg.studio.tuktuk.util.collections;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nonblocking stack, array based.
 *
 *
 */
public class ConcArrayStack<T> {

	private ArrayList<T> array = new ArrayList<T>();
	private final ReentrantLock lock = new ReentrantLock();
	
	public void push(T obj) {
		lock.lock();
		array.add(obj);
		lock.unlock();
	}
	
	public T pop() {
		T ret = null;
		
		lock.lock();
		
		int s = array.size();
		if(s > 0)
			ret = array.remove( s-1 );
		
		lock.unlock();
		
		return ret;
	}
}
