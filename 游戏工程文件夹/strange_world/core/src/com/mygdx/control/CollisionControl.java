package com.mygdx.control;

import com.mygdx.actor.BaseActor;
import com.mygdx.ai.Step;
import com.mygdx.world.TiledMapSystem;

/**
 * ��ײ��������
 * @author ��˳
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
		isPass(actor);
	}


	
	public static boolean isPass(BaseActor actor) {
		/* <���ͼ����ײ���>
		 * 1,�㳤���ռ�������ӣ�32px�����õ����Ե���
		 * 2���ַ����������
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
		boolean isPass=true;//�Ƿ��ͨ���ı�־
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
				//������Ե�㷨
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
				//������Ե�㷨
				float x=((int) ((actor.getX()+actor.getWidth()+actor.getSpeed())/TiledMapSystem.MAP_TILE_WIDTH)+1)*TiledMapSystem.MAP_TILE_WIDTH-actor.getWidth()-1;
				actor.setX(x);
			}		
			break;
		case MoveControl.STATE_UP:
			for (int i = 0; i < w-1; i++) {
				if (!TiledMapSystem.passEnble(actor.getX()+TiledMapSystem.MAP_TILE_WIDTH*i, actor.getY()+actor.getHeight()+actor.getSpeed())) {
					isPass=false;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth(), actor.getY()+actor.getHeight()+actor.getSpeed())) {
				isPass=false;
			}
			if (isPass==false) {
				actor.setSpeed(MoveControl.SPEED_0);
				//������Ե�㷨
				float y=((int) ((actor.getY()+actor.getHeight()+actor.getSpeed())/TiledMapSystem.MAP_TILE_HEIGHT)+1)*TiledMapSystem.MAP_TILE_HEIGHT-actor.getHeight()-2;
				actor.setY(y);
			}
			break;
		case MoveControl.STATE_DOWN:
			for (int i = 0; i < w-1; i++) {
				if (!TiledMapSystem.passEnble(actor.getX()+TiledMapSystem.MAP_TILE_WIDTH*i, actor.getY()-actor.getSpeed())) {
					isPass=false;
					break;
				}
			}
			if (!TiledMapSystem.passEnble(actor.getX()+actor.getWidth(), actor.getY()-actor.getSpeed())) {
				isPass=false;
			}
			if (isPass==false) {
				actor.setSpeed(MoveControl.SPEED_0);
				//������Ե�㷨
				float y=((int) ((actor.getY()-actor.getSpeed())/TiledMapSystem.MAP_TILE_HEIGHT))*TiledMapSystem.MAP_TILE_HEIGHT;
				actor.setY(y);
			}
			break;
		default:
			break;
		}
		return isPass;
	}

	public static boolean canReach(Step step,BaseActor ba) {
		BaseActor actor=new BaseActor(step.x*TiledMapSystem.MAP_TILE_WIDTH, step.y*TiledMapSystem.MAP_TILE_HEIGHT, ba.getWidth(), ba.getHeight(), ba.getType(), ba.getWorld());
		return isPass(actor);
	}
	public static boolean canReach(int x ,int y, BaseActor ba) {
		BaseActor actor=new BaseActor(x*TiledMapSystem.MAP_TILE_WIDTH, y*TiledMapSystem.MAP_TILE_HEIGHT, ba.getWidth(), ba.getHeight(), ba.getType(), ba.getWorld());
		return isPass(actor);
	}
}
