package com.mygdx.control;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.mygdx.actor.BaseActor;
import com.mygdx.game.MyGdxGame;
import com.mygdx.world.TiledMapSystem;
import com.mygdx.world.World;

/**
 * 碰撞检测控制器
 * @author 张顺
 */
public class CollisionActorControl implements IControl{

	private BaseActor actor;
	private Stage stage;
	private Array<Actor> as;
	
	
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	
	
	
	public CollisionActorControl(BaseActor actor) {
		this.actor = actor;
		this.stage=actor.getStage();
	}
	
	
	@Override
	public void initialize() {
		as=stage.getActors();
	}

	@Override
	public void update() {
		initialize();
		/* <与地图的碰撞检测>
		 * 1,算长宽各占几个格子（32px），得到测试点数
		 * 2，分方向检测各监测点
		 * */
		/*
		 * 2016-10-22 16:30:58 张顺
		 * 各个actor之间的碰撞检测
		 */
		System.out.println("---->>"+as.size);
		for (int i = 0; i < as.size; i++) {
			if (as.get(i) instanceof BaseActor) {
				BaseActor bs=(BaseActor) as.get(i);
				//这里做个判断，如果是自己就不处理，避免自己跟自己比
				if (bs.equals(actor)) {
					continue;
				}
				//开始进行碰撞检测
				float speedtmp=actor.getSpeedy()+World.GRAVITY*MyGdxGame.TIME_INTERVAL;
				float ytmp=(actor.getY()+speedtmp*MyGdxGame.TIME_INTERVAL);
				Rectangle rec=null;
				switch (actor.getState()) {
				case MoveControl.STATE_LEFT:
					rec=new Rectangle(actor.getX()-actor.getSpeed(), ytmp, actor.getWidth(), actor.getHeight());//下一步的矩形
					break;
				case MoveControl.STATE_RIGHT:
					rec=new Rectangle(actor.getX()-actor.getSpeed(), ytmp, actor.getWidth(), actor.getHeight());//下一步的矩形
					break;
				default:
					rec=new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());//下一步的矩形
					break;
				}
//				System.out.println("x"+rec.getX()+"y"+rec.getY());
//				System.out.println("x"+bs.getRectangle().getX()+"y"+bs.getRectangle().getY());
//				System.out.println(rec.overlaps(bs.getRectangle())+"  x1"+rec.getX()+"y1"+rec.getY()+" x2"+bs.getX()+"y1"+bs.getY());
				if (rec.overlaps(bs.getRectangle())) {//碰撞了
					actor.setSpeed(MoveControl.SPEED_0);
//					actor.setState(0-actor.getState());
//					System.out.println("111111111111111");
					break;
				}else {
//					actor.setSpeed(MoveControl.SPEED_1);
//					System.out.println("222222222222222");
				}
			}
		}
		
		
		
		
	}



}
