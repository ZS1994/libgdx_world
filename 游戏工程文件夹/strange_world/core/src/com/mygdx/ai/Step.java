package com.mygdx.ai;

public class Step {
	
	private float w;
	private float h;
	private float x;
	private float y;
	
	private Step stepChild;//�ӽڵ�
	private Step stepParent;//���ڵ�
	

	
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
	
	
	/**
	 * ��˳ 2016-10-18
	 * ��ΪѰ·�㷨�Ĳ�
	 * @param w ��
	 * @param h ��
	 * @param x ���x
	 * @param y ���y
	 */
	public Step(float w, float h, float x, float y) {
		this.w = w;
		this.h = h;
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return "Step [x=" + x + ", y=" + y + "]";
	}
	
	
	
	
	
}
