package com.mygdx.ai;

import com.mygdx.actor.BaseActor;
import com.mygdx.world.TiledMapSystem;

public class Step {
	
	public float w;
	public float h;
	//以下是格子坐标，并不是真实坐标
	public float x;
	public float y;
	
	public float F,H,G;//用于计算最优解
	
	public Step stepChild;//
	public Step stepParent;//
	

	
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public Step getStepChild() {
		return stepChild;
	}
	public void setStepChild(Step stepChild) {
		this.stepChild = stepChild;
	}
	public Step getStepParent() {
		return stepParent;
	}
	public void setStepParent(Step stepParent) {
		this.stepParent = stepParent;
	}
	
	public Step(float w, float h, float x, float y) {
		this.w = w;
		this.h = h;
		this.x = x;
		this.y = y;
	}
	
	
	
	public Step(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		this.w=TiledMapSystem.MAP_TILE_WIDTH;
		this.h=TiledMapSystem.MAP_TILE_HEIGHT;
	}
	
	
	@Override
	public String toString() {
		return "Step [x=" + x + ", y=" + y + "]";
	}
	
	public void calcF() {  
        this.F = this.G + this.H;  
    }  
	
	
	@Override
	public boolean equals(Object arg0) {
		Step step=(Step) arg0;
		if (x==step.x && y==step.y) {
			return true;
		}else {
			return false;
		}
	}
	
}
