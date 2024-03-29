package com.mg.studio.tuktuk.util.pool;

import com.mg.studio.tuktuk.util.collections.ConcNodeCachingStack;

public abstract class ConcOneClassPool<T> {
	private ConcNodeCachingStack<T> objs;

	public ConcOneClassPool() {
		objs = new ConcNodeCachingStack<T>();
	}

	protected abstract T allocate();
	
	public T get() {
		T ret = objs.pop();
		if(null == ret) {
			ret = allocate();
		}

		return ret;
	}
	
	public void free(T obj) {
		objs.push(obj);
	}
}
