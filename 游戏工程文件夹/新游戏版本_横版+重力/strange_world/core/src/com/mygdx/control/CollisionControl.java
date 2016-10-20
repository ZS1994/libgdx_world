package com.mygdx.control;

import com.mygdx.actor.BaseActor;
import com.mygdx.world.TiledMapSystem;

/**
 * 碰撞检测控制器
 * @author 张顺
 */
public class CollisionControl implements IControl{

	private BaseActor actor;
	private int isCling=0;
	
	public static final int CLING_NO=0;
	public static final int CLING_YES=1;
	
	
	
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	
	
	public CollisionControl(BaseActor actor) {
		this.actor = actor;
	}
	
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void update() {
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
		switch (actor.getState()) {
		case MoveControl.STATE_LEFT:
			for (int i = 0; i < h-1; i++) {
				if (!TiledMapSystem.passEnble(actor.getX()-actor.getSpeed(), actor.getY()+TiledMapSystem.MAP_TILE_HEIGHT*i)) {
					isPass=false;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()-actor.getSpeed(), actor.getY()+actor.getHeight())) {
				isPass=false;
			}
			if (isPass==false) {
				actor.setSpeed(MoveControl.SPEED_0);
				//贴紧边缘算法
				float x=((int) ((actor.getX()-actor.getSpeed())/TiledMapSystem.MAP_TILE_WIDTH))*TiledMapSystem.MAP_TILE_WIDTH;
				actor.setX(x);
			}
			break;
		case MoveControl.STATE_RIGHT:
			for (int i = 0; i < h-1; i++) {
				if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth()+actor.getSpeed(), actor.getY()+TiledMapSystem.MAP_TILE_HEIGHT*i)) {
					isPass=false;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth()+actor.getSpeed(), actor.getY()+actor.getHeight())) {
				isPass=false;
			}
			if (isPass==false) {
				actor.setSpeed(MoveControl.SPEED_0);
				//贴紧边缘算法
				float x=((int) ((actor.getX()+actor.getWidth()+actor.getSpeed())/TiledMapSystem.MAP_TILE_WIDTH)+1)*TiledMapSystem.MAP_TILE_WIDTH-actor.getWidth()-1;
				actor.setX(x);
			}		
			break;
		default:
			break;
		}
		
		
		
	}



}
