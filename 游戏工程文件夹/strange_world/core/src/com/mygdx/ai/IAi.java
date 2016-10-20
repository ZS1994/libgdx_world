package com.mygdx.ai;

public interface IAi {
	
	/**
	 * 装载属性
	 */
	public void initialize();
	
	/**
	 * 更新状态（实现移动）
	 */
	public void update();
	
	
}
