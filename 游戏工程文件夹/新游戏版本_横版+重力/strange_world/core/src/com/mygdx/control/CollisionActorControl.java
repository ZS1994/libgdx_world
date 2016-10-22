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
 * ��ײ��������
 * @author ��˳
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
		/* <���ͼ����ײ���>
		 * 1,�㳤���ռ�������ӣ�32px�����õ����Ե���
		 * 2���ַ����������
		 * */
		/*
		 * 2016-10-22 16:30:58 ��˳
		 * ����actor֮�����ײ���
		 */
		System.out.println("---->>"+as.size);
		for (int i = 0; i < as.size; i++) {
			if (as.get(i) instanceof BaseActor) {
				BaseActor bs=(BaseActor) as.get(i);
				//���������жϣ�������Լ��Ͳ����������Լ����Լ���
				if (bs.equals(actor)) {
					continue;
				}
				//��ʼ������ײ���
				float speedtmp=actor.getSpeedy()+World.GRAVITY*MyGdxGame.TIME_INTERVAL;
				float ytmp=(actor.getY()+speedtmp*MyGdxGame.TIME_INTERVAL);
				Rectangle rec=null;
				switch (actor.getState()) {
				case MoveControl.STATE_LEFT:
					rec=new Rectangle(actor.getX()-actor.getSpeed(), ytmp, actor.getWidth(), actor.getHeight());//��һ���ľ���
					break;
				case MoveControl.STATE_RIGHT:
					rec=new Rectangle(actor.getX()-actor.getSpeed(), ytmp, actor.getWidth(), actor.getHeight());//��һ���ľ���
					break;
				default:
					rec=new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());//��һ���ľ���
					break;
				}
//				System.out.println("x"+rec.getX()+"y"+rec.getY());
//				System.out.println("x"+bs.getRectangle().getX()+"y"+bs.getRectangle().getY());
//				System.out.println(rec.overlaps(bs.getRectangle())+"  x1"+rec.getX()+"y1"+rec.getY()+" x2"+bs.getX()+"y1"+bs.getY());
				if (rec.overlaps(bs.getRectangle())) {//��ײ��
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
