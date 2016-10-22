package com.mygdx.control;

import com.mygdx.actor.BaseActor;
import com.mygdx.game.MyGdxGame;
import com.mygdx.world.TiledMapSystem;
import com.mygdx.world.World;

/**
 * 碰撞检测控制器
 * @author 张顺
 */
public class CollisionControl implements IControl{

	private BaseActor actor;
	
	
	
	
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
				if (!TiledMapSystem.passEnble(actor.getX()-actor.getSpeed()-1, actor.getY()+TiledMapSystem.MAP_TILE_HEIGHT*i)) {
					isPass=false;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()-actor.getSpeed()-1, actor.getY()+actor.getHeight())) {
				isPass=false;
			}
			if (isPass==false) {
				actor.setSpeed(MoveControl.SPEED_0);
				//贴紧边缘算法
				float x=((int) ((actor.getX()-actor.getSpeed())/TiledMapSystem.MAP_TILE_WIDTH))*TiledMapSystem.MAP_TILE_WIDTH;
				actor.setX(x);
			}else {
				actor.setSpeed(MoveControl.SPEED_1);
			}
			break;
		case MoveControl.STATE_RIGHT:
			for (int i = 0; i < h-1; i++) {
				if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth()+actor.getSpeed()+1, actor.getY()+TiledMapSystem.MAP_TILE_HEIGHT*i)) {
					isPass=false;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth()+actor.getSpeed()+1, actor.getY()+actor.getHeight())) {
				isPass=false;
			}
			if (isPass==false) {
				actor.setSpeed(MoveControl.SPEED_0);
				//贴紧边缘算法
				float x=((int) ((actor.getX()+actor.getWidth()+actor.getSpeed())/TiledMapSystem.MAP_TILE_WIDTH)+1)*TiledMapSystem.MAP_TILE_WIDTH-actor.getWidth()-1;
				actor.setX(x);
			}else {
				actor.setSpeed(MoveControl.SPEED_1);
			}		
			break;
		default:
			break;
		}
		//最大下降速度限制
		if (actor.getSpeedy()<=World.SPEED_DOWN_MAX) {
			actor.setSpeedy(World.SPEED_DOWN_MAX);
		}
		//检测垂直方向的碰撞检测+重力系统
		boolean isPass2=true;//垂直方向的是否通过的标志
		float speedtmp=actor.getSpeedy()+World.GRAVITY*MyGdxGame.TIME_INTERVAL;
		float ytmp=(actor.getY()+speedtmp*MyGdxGame.TIME_INTERVAL);
		if (actor.getSpeedy()>0) {//往上跳
			//往上的头顶的检测点点的y坐标
			float xtmp=0;//记录碰撞的x坐标
			for (int i = 0; i < w-1; i++) {
				if (!TiledMapSystem.passEnble(actor.getX()+TiledMapSystem.MAP_TILE_WIDTH*i, ytmp+actor.getHeight())) {
					isPass2=false;
					xtmp=actor.getX()+TiledMapSystem.MAP_TILE_WIDTH*i;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth(), ytmp+actor.getHeight())) {
				isPass2=false;
				xtmp=actor.getX()+actor.getWidth();
			}
			if (!isPass2) {
				//贴紧边缘算法
				//需要一个找边缘的算法
				actor.setSpeedy(0);
				float y=((int) ((ytmp+actor.getHeight())/TiledMapSystem.MAP_TILE_HEIGHT))*TiledMapSystem.MAP_TILE_HEIGHT;
				for (int i = 0;TiledMapSystem.passEnble(xtmp, y)==false ; i++) {
					y=y-TiledMapSystem.MAP_TILE_HEIGHT*i;
				}
				y=y+TiledMapSystem.MAP_TILE_HEIGHT-actor.getHeight();
				System.out.println(y);
				actor.setY(y);
			}else {
				actor.setSpeedy(speedtmp);
				actor.setY(ytmp);
			}
			actor.setJump(true);
		}else if (actor.getSpeedy()<=0) {//往下掉
			//往下的检测点
			float xtmp=0;//记录碰撞的x坐标
			for (int i = 0; i < w-1; i++) {
				if (!TiledMapSystem.passEnble(actor.getX()+TiledMapSystem.MAP_TILE_WIDTH*i, ytmp)) {
					isPass2=false;
					xtmp=actor.getX()+TiledMapSystem.MAP_TILE_WIDTH*i;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth(), ytmp)) {
				xtmp=actor.getX()+actor.getWidth();
				isPass2=false;
			}
			
			if (!isPass2) {
				//贴紧边缘算法
				float y=((int) (ytmp/TiledMapSystem.MAP_TILE_HEIGHT) +1)*TiledMapSystem.MAP_TILE_HEIGHT;
				actor.setSpeedy(0);
				actor.setJump(false);
				for (int i = 0;TiledMapSystem.passEnble(xtmp, y)==false ; i++) {
					y=y+TiledMapSystem.MAP_TILE_HEIGHT*i;
					break;
				}
				actor.setY(y);
			}else {
				actor.setSpeedy(speedtmp);
				actor.setJump(true);
				actor.setY(ytmp);
			}
		}
		
		
	}



}
