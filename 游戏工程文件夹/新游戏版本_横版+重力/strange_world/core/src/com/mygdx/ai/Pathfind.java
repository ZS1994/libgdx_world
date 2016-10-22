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
 *<br>2016-10-22 16:00:03现在改为2d横版的寻路
 */
public class Pathfind implements IControl{
	
	
	private BaseActor actorEnd;
	private BaseActor actor;
	private float xend,yend;
	//------定时器---------
	private float time1,time2;
	private float timeInterval;
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	public BaseActor getActorEnd() {
		return actorEnd;
	}
	public void setActorEnd(BaseActor actorEnd) {
		this.actorEnd = actorEnd;
	}
	public float getXend() {
		return xend;
	}
	public void setXend(float xend) {
		this.xend = xend;
	}
	public float getYend() {
		return yend;
	}
	public void setYend(float yend) {
		this.yend = yend;
	}
	
	
	public Pathfind(BaseActor actorStart,BaseActor actorEnd,float timeInterval) {
		this.actor = actorStart;
		this.actorEnd=actorEnd;
		this.timeInterval=timeInterval;
		initialize();
	}
	
	
	@Override
	public void initialize() {
		xend=actorEnd.getX();
		yend=actorEnd.getY();
	}

	
	
	/**
	 * 安照路线来控制state，从而控制移动
	 * @param stepMain
	 */
	public void update() {
		time1=actor.getStateTime();
		if (time2==0) {
			time2=actor.getStateTime();
		}
		if (time1-time2>=3) {
			initialize();
			time2=0;
		}
		if (actor.getX()<=xend-TiledMapSystem.MAP_TILE_WIDTH) {
			actor.setState(MoveControl.STATE_RIGHT);
			actor.setStateLast(MoveControl.STATE_RIGHT);
		}else if (actor.getX()>=xend+TiledMapSystem.MAP_TILE_WIDTH) {
			actor.setState(MoveControl.STATE_LEFT);
			actor.setStateLast(MoveControl.STATE_LEFT);
		}else {
			actor.setState(MoveControl.STATE_WAIT);
		}
		
		if (actor.getY()<yend && actor.isJump()==false) {
			actor.setSpeedy(100);
			actor.setJump(true);
		}
		
		
	}
	
	
	
	
	
}
