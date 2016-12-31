package com.mygdx.math;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 我要实现的是数据的向量.
 * 我的目的是想用于游戏演员类的移动系统设计
 * 2016-12-29
 * @author 张顺
 */
public class Vector {

	private double direction;//方向，单位是度，�?�?360�?
	private double magnitude;//大小
	private Point points[]=new Point[2];//初始化一个起点终点备�?
	
	public Vector() {
	}
	
	/**
	 * 方向、大小构造法
	 */
	public Vector(double direction, double magnitude) {
		super();
		this.direction = direction;
		this.magnitude = magnitude;
	}
	
	/**
	 * 两点构造法
	 */
	public Vector(Point p1,Point p2) {
		super();
		double x1=p1.getX();
		double y1=p1.getY();
		double x2=p2.getX();
		double y2=p2.getY();
		direction=new BigDecimal(Math.toDegrees(Math.atan((y2-y1)/(x2-x1)))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		magnitude=new BigDecimal(Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 向量加法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector add(Vector v1,Vector v2) {
		//将v1变成以（0,0）为起点的向�?
		Point pp1[]=v1.getPoints(new Point(0, 0));
		//将v2变成以v1�?(x2,y2)为起点的向量
		Point pp2[]=v2.getPoints(pp1[1]);
		//计算得到新向�?
		Point pp3[]=new Point[2];
		pp3[0]=pp1[0];
		pp3[1]=pp2[1];
		Vector resVec=new Vector(pp3[0], pp3[1]);
		return resVec;
	}
	
	/*计算起点终点坐标*/
	private Point[] getPoints(Point p1){
		points[0]=p1;
		double x2=p1.getX()+Math.cos(Math.toRadians(direction))*magnitude;
		double y2=p1.getY()+Math.sin(Math.toRadians(direction))*magnitude;
		points[1]=new Point(x2, y2);
		return points;
	}
	
	
	//------------------------------------------
	
	public double getDirection() {
		return direction;
	}
	public void setDirection(double direction) {
		this.direction = direction;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	@Override
	public String toString() {
		return "Vector [direction=" + direction + ", magnitude=" + magnitude
				+ ", points=" + Arrays.toString(points) + "]";
	}

	//--------------------------------------------
	
	
}
