package com.mygdx.ai;

import java.util.ArrayList;
import java.util.List;

import com.mygdx.actor.BaseActor;
import com.mygdx.control.IControl;
import com.mygdx.control.MoveControl;
import com.mygdx.tools.Transform;
import com.mygdx.world.TiledMapSystem;

/**
 * 【寻路算法】
 * @author 张顺
 *2016年10月17日17:51:33
 */
public class Pathfind implements IControl{
	
	
	private BaseActor actorEnd;
	private BaseActor actor;
	private float x;
	private float y;
	private float xend;
	private float yend;
	private Step stepNow;//临时变量用于移动
	private List<Step> stepOn=new ArrayList<Step>();//开启列表
	private List<Step> stepOff=new ArrayList<Step>();//关闭列表
	//测试用
	String[][] str=new String[100][100];
	//定时器
	float time1,time2;
	
	
	public List<Step> getStepOn() {
		return stepOn;
	}
	public void setStepOn(List<Step> stepOn) {
		this.stepOn = stepOn;
	}
	public List<Step> getStepOff() {
		return stepOff;
	}
	public void setStepOff(List<Step> stepOff) {
		this.stepOff = stepOff;
	}
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}

	


	public Pathfind(BaseActor actorStart,BaseActor actorEnd) {
		this.actor = actorStart;
		this.actorEnd=actorEnd;
		initialize();
	}
	
	
	
	/**
	 * 组装路线
	 * @param stepMain
	 */
	private void assembly (Step stepMain) {
		//当到达了终点时停止
		if(Transform.xToxIndex(stepMain.getX())==Transform.xToxIndex(xend) && Transform.xToxIndex(stepMain.getY())==Transform.xToxIndex(yend)) {
			return;
		}
		
		Step step1=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX(), stepMain.getY()+stepMain.getH());//上
		Step step2=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX(), stepMain.getY()-stepMain.getH());//下
		Step step3=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX()-stepMain.getW(), stepMain.getY());//左
		Step step4=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX()+stepMain.getW(), stepMain.getY());//右
		
		getStepOn().add(step1);
		getStepOn().add(step2);
		getStepOn().add(step3);    
		getStepOn().add(step4);
		
		getStepOff().add(stepMain);//将起点加入关闭列表
		
		//与地图的碰撞检测,将碰撞不通过的加入关闭列表
		/* <与地图的碰撞检测>
		 * 1,算长宽各占几个格子（32px），得到测试点数
		 * 2，分方向检测各监测点
		 * */
		int w=(int)actor.getWidth()/TiledMapSystem.MAP_TILE_WIDTH;
		int h=(int)actor.getHeight()/TiledMapSystem.MAP_TILE_HEIGHT;
		if (actor.getWidth()>w*TiledMapSystem.MAP_TILE_WIDTH) {
			w=w+2;
		}else {
			w=w+1;
		}
		if (actor.getHeight()>h*TiledMapSystem.MAP_TILE_HEIGHT) {
			h=h+2;
		}else {
			h=h+1;
		}
		boolean isPass=true;//是否可通过的标志
		//检测上
		for (int i = 0; i < w-1; i++) {
			if (!TiledMapSystem.passEnble(step1.getX()+TiledMapSystem.MAP_TILE_WIDTH*i, step1.getY()+actor.getHeight())) {
				getStepOff().add(step1);
				break;
			}
		}
		if (!TiledMapSystem.passEnble(step1.getX()+actor.getWidth(), step1.getY()+actor.getHeight())) {
			getStepOff().add(step1);
		}
		//检测下
		for (int i = 0; i < w-1; i++) {
			if (!TiledMapSystem.passEnble(step2.getX()+TiledMapSystem.MAP_TILE_WIDTH*i, step2.getY())) {
				getStepOff().add(step2);
				break;
			}
		}
		if (!TiledMapSystem.passEnble(step2.getX()+actor.getWidth(), step2.getY())) {
			getStepOff().add(step2);
		}
		//检测左
		for (int i = 0; i < h-1; i++) {
			if (!TiledMapSystem.passEnble(step3.getX(), step3.getY()+TiledMapSystem.MAP_TILE_HEIGHT*i)) {
				getStepOff().add(step3);
				break;
			}
		}
		if (!TiledMapSystem.passEnble(step3.getX(), step3.getY()+actor.getHeight())) {
			getStepOff().add(step3);
		}
		//检测右
		for (int i = 0; i < h-1; i++) {
			if (!TiledMapSystem.passEnble(step4.getX()+actor.getWidth(), step4.getY()+TiledMapSystem.MAP_TILE_HEIGHT*i)) {
				getStepOff().add(step4);
				break;
			}
		}
		if (!TiledMapSystem.passEnble(step4.getX()+actor.getWidth(), step4.getY()+actor.getHeight())) {
			getStepOff().add(step4);
		}
			
			
		
		//去掉开启列表中的关闭列表的项
		for (int i = getStepOn().size()-1; i >=0 ; i--) {
			for (int j = 0; j < getStepOff().size(); j++) {
				if (getStepOn().get(i).getX()==getStepOff().get(j).getX() && getStepOn().get(i).getY()==getStepOff().get(j).getY()) {
					getStepOn().remove(i);
					break;
				}
			}
		}
		
		/*
		 * f=h+g。一定相等了，因为只能上下左右移动并且速度都为speed，找h最小的就行了。
		 */
		//计算f，选择f最小的，从开启列表中去掉大的，其余的加入关闭列表，开启列表就剩下最小的那个了
		//现在是比h，找h小的成功了
		for (int i = getStepOn().size()-1; i >0; i--) {
			float h1=Transform.xToxIndex(Math.abs(getStepOn().get(i).getX()-xend))+Transform.xToxIndex(Math.abs(getStepOn().get(i).getY()-yend));
			float h2=Transform.xToxIndex(Math.abs(getStepOn().get(i-1).getX()-xend))+Transform.xToxIndex(Math.abs(getStepOn().get(i-1).getY()-yend));
			float g1=Transform.xToxIndex(Math.abs(getStepOn().get(i).getX()-x))+Transform.xToxIndex(Math.abs(getStepOn().get(i).getY()-y));
			float g2=Transform.xToxIndex(Math.abs(getStepOn().get(i-1).getX()-x))+Transform.xToxIndex(Math.abs(getStepOn().get(i-1).getY()-y));
			float f1=h1+g1;
			float f2=h2+g2;
			if (h1>h2) { 
				getStepOff().add(getStepOn().get(i));
				getStepOn().remove(getStepOn().get(i));
			}else {
				getStepOff().add(getStepOn().get(i-1));
				getStepOn().remove(getStepOn().get(i-1));
			}
		}
		
		//从开启列表中拿出剩下的，重新从这个再来处理
		if (getStepOn().size()>0) {
			Step steptmp=getStepOn().get(0);
			stepMain.setStepChild(steptmp);
			steptmp.setStepParent(stepMain);
			assembly(steptmp);
		}
	}
	
	
	
	/**
	 * 安照路线来控制state，从而控制移动
	 * @param stepMain
	 */
	public void update() {
		Step steptmp=stepNow.getStepChild();
		if (steptmp!=null) {
//			System.out.println("actor:"+actor.getX()+" "+actor.getY());
//			System.out.println("stepNow:"+stepNow.getX()+" "+stepNow.getY());
//			System.out.println("state:"+actor.getState());
//			System.out.println("steptmp:"+steptmp.getX()+" "+steptmp.getY());
//			System.out.println("------------------------------");
			if (actor.getX()==steptmp.getX() && actor.getY()==steptmp.getY()) {
				stepNow=steptmp;
				actor.setState(MoveControl.STATE_WAIT);
				return;
			}else {
				if (steptmp.getX()<stepNow.getX()) {//往左
					actor.setState(MoveControl.STATE_LEFT);
					actor.setStateLast(MoveControl.STATE_LEFT);
				}else if (steptmp.getX()>stepNow.getX()) {
					actor.setState(MoveControl.STATE_RIGHT);
					actor.setStateLast(MoveControl.STATE_RIGHT);
				}else if (steptmp.getY()>stepNow.getY()) {
					actor.setState(MoveControl.STATE_UP);
				}else if (steptmp.getY()<stepNow.getY()) {
					actor.setState(MoveControl.STATE_DOWN);
				}else {
					actor.setState(MoveControl.STATE_WAIT);
				}
			}
		}else {
			actor.setState(MoveControl.STATE_WAIT);
			if (time1==0) {
				time1=actor.getStateTime();
			}
			time2=actor.getStateTime();
			if (time2-time1>=5) {
				time1=0;
				time2=0;
				initialize();
			}
			
		}
	}
	
	@Override
	public void initialize() {
		getStepOn().clear();
		getStepOff().clear();
		this.x = actor.getX();
		this.y = actor.getY();
		this.xend = actorEnd.getX();
		this.yend = actorEnd.getY();
		stepNow=new Step(TiledMapSystem.MAP_TILE_WIDTH, TiledMapSystem.MAP_TILE_HEIGHT, actor.getX(), actor.getY());
		assembly(stepNow);
//		tell(getStepOn().get(0));
//		tell2(stepNow);
//		tell4();
	}
	
	
	
	
	private void tell(Step s) {
		System.out.println(s.toString());
		if (s.getStepParent()!=null) {
			tell(s.getStepParent());
		}
		
	}
	private void tell2(Step s) {
		System.out.println(s.toString());
		if (s.getStepChild()!=null) {
			tell2(s.getStepChild());
		}
	}
	
	private void tell3(Step s) {
		str[Transform.xToxIndex(s.getY())][Transform.xToxIndex(s.getX())]="◆";
		if (s.getStepChild()!=null) {
			tell3(s.getStepChild());
		}
	}
	
	private void tell4(){
		System.out.println("实际路径————————————————————————");
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < str[i].length; j++) {
				str[i][j]="◇";
			}
		}
		tell3(stepNow);
		str[Transform.xToxIndex(y)][Transform.xToxIndex(x)]="★";
		str[Transform.xToxIndex(yend)][Transform.xToxIndex(xend)]="☆";
		for (int i = str.length-1; i > 0; i--) {
			for (int j = 0; j < str[i].length; j++) {
				System.out.print(str[i][j]);
			}
			System.out.println();
		}
		System.out.println("关闭列表————————————————————————");
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < str[i].length; j++) {
				str[i][j]="◇";
			}
		}
		for (int i = 0; i < getStepOff().size(); i++) {
			str[Transform.xToxIndex(getStepOff().get(i).getY())][Transform.xToxIndex(getStepOff().get(i).getX())]="◆";
		}
		for (int i = str.length-1; i > 0; i--) {
			for (int j = 0; j < str[i].length; j++) {
				System.out.print(str[i][j]);
			}
			System.out.println();
		}
	}
	
	
	
}
