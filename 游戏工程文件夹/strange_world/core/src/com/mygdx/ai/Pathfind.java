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
	/*
	 * f=h+g。一定相等了，因为只能上下左右移动并且速度都为speed，找h最小的就行了。
	 */
	
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
	String[][] str=new String[30][30];
	
	
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
		this.x = actorStart.getX();
		this.y = actorStart.getY();
		this.xend = actorEnd.getX();
		this.yend = actorEnd.getY();
		stepNow=new Step(actorStart.getWidth(), actorStart.getHeight(), actorStart.getX(), actorStart.getY());
		assembly(stepNow);
//		tell(getStepOn().get(0));
//		tell2(stepNow);
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
	
	
	
	/**
	 * 组装路线
	 * @param stepMain
	 */
	private void assembly (Step stepMain) {
		Step steptmp=null;
		if (stepMain==null){
			return;
		}else {
			//当到达了终点时停止
			if( stepMain.getX()>actorEnd.getX()-actor.getSpeed() &&
				stepMain.getX()<actorEnd.getX()+actor.getSpeed() &&
				stepMain.getY()>actorEnd.getY()-actor.getSpeed() &&
				stepMain.getY()<actorEnd.getY()+actor.getSpeed()
				) {
				return;
			}
			
			Step step1=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX(), stepMain.getY()+actor.getSpeed());
			Step step2=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX(), stepMain.getY()-actor.getSpeed());
			Step step3=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX()-actor.getSpeed(), stepMain.getY());
			Step step4=new Step(stepMain.getW(), stepMain.getH(), stepMain.getX()+actor.getSpeed(), stepMain.getY());
			
			getStepOn().add(step1);
			getStepOn().add(step2);
			getStepOn().add(step3);
			getStepOn().add(step4);
			
			getStepOff().add(stepMain);//将起点加入关闭列表
			
			//去掉父节点
//			if (stepMain.getStepParent()!=null) {
//				for (int i = getStepOn().size()-1; i >= 0; i--) {
//					if (stepMain.getStepParent().getX()==getStepOn().get(i).getX() && stepMain.getStepParent().getY()==getStepOn().get(i).getY()) {
//						getStepOn().remove(i);
//					}
//				}
//			}
			
			//与地图的碰撞检测,将碰撞不通过的加入关闭列表
			for (int i = 0; i <getStepOn().size() ; i++) {
				if (!TiledMapSystem.passEnble(getStepOn().get(i).getX(), getStepOn().get(i).getY())) {
					getStepOff().add(getStepOn().get(i));
				}
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
			
			
			//计算f，选择f最小的，从开启列表中去掉大的，其余的加入关闭列表，开启列表就剩下最小的那个了
			for (int i = getStepOn().size()-1; i >0; i--) {
				float h1=Math.abs(getStepOn().get(i).getX()-xend)+Math.abs(getStepOn().get(i).getY()-yend);
				float h2=Math.abs(getStepOn().get(i-1).getX()-xend)+Math.abs(getStepOn().get(i-1).getY()-yend);
				float g1=Math.abs(getStepOn().get(i).getX()-x)+Math.abs(getStepOn().get(i).getY()-y);
				float g2=Math.abs(getStepOn().get(i-1).getX()-x)+Math.abs(getStepOn().get(i-1).getY()-y);
				float f1=h1+g1*100;
				float f2=h2+g2*100;
				if (f1>=f2) { 
					getStepOff().add(getStepOn().get(i));
					getStepOn().remove(getStepOn().get(i));
				}else {
					getStepOff().add(getStepOn().get(i-1));
					getStepOn().remove(getStepOn().get(i-1));
				}
			}
			
			//从开启列表中拿出剩下的，重新从这个再来处理
			if (getStepOn().size()>0) {
				steptmp=getStepOn().get(0);
				stepMain.setStepChild(steptmp);
				steptmp.setStepParent(stepMain);
			}
		}
//		System.out.println(steptmp.getX()+"  "+steptmp.getY());
		assembly(steptmp);
	}
	
	
	
	/**
	 * 安照路线来控制state，从而控制移动
	 * @param stepMain
	 */
	public void update() {
		/*
		 */
		Step steptmp=stepNow.getStepChild();
		if (steptmp!=null) {
//			System.out.println("actor:"+actor.getX()+" "+actor.getY());
//			System.out.println("steptmp:"+steptmp.getX()+" "+steptmp.getY());
//			System.out.println("stepNow:"+stepNow.getX()+" "+stepNow.getY());
//			System.out.println("state:"+actor.getState());
//			System.out.println("------------------------------");
			if (actor.getX()==steptmp.getX() && actor.getY()==steptmp.getY()) {
				stepNow=steptmp;
				return;
			}else {
				if (steptmp.getX()<stepNow.getX()) {//往左
					actor.setState(MoveControl.STATE_LEFT);
				}else if (steptmp.getX()>stepNow.getX()) {
					actor.setState(MoveControl.STATE_RIGHT);
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
		}
//		actor.setState(MoveControl.STATE_LEFT);  
		
	}
	
	@Override
	public void initialize() {
		
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
	
}
