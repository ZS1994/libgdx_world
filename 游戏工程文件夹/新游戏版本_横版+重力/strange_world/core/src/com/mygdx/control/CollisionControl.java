package com.mygdx.control;

import com.mygdx.actor.BaseActor;
import com.mygdx.game.MyGdxGame;
import com.mygdx.world.TiledMapSystem;
import com.mygdx.world.World;

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
				//������Ե�㷨
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
				//������Ե�㷨
				float x=((int) ((actor.getX()+actor.getWidth()+actor.getSpeed())/TiledMapSystem.MAP_TILE_WIDTH)+1)*TiledMapSystem.MAP_TILE_WIDTH-actor.getWidth()-1;
				actor.setX(x);
			}else {
				actor.setSpeed(MoveControl.SPEED_1);
			}		
			break;
		default:
			break;
		}
		//����½��ٶ�����
		if (actor.getSpeedy()<=World.SPEED_DOWN_MAX) {
			actor.setSpeedy(World.SPEED_DOWN_MAX);
		}
		//��ⴹֱ�������ײ���+����ϵͳ
		boolean isPass2=true;//��ֱ������Ƿ�ͨ���ı�־
		float speedtmp=actor.getSpeedy()+World.GRAVITY*MyGdxGame.TIME_INTERVAL;
		float ytmp=(actor.getY()+speedtmp*MyGdxGame.TIME_INTERVAL);
		if (actor.getSpeedy()>0) {//������
			//���ϵ�ͷ���ļ�����y����
			float xtmp=0;//��¼��ײ��x����
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
				//������Ե�㷨
				//��Ҫһ���ұ�Ե���㷨
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
		}else if (actor.getSpeedy()<=0) {//���µ�
			//���µļ���
			float xtmp=0;//��¼��ײ��x����
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
				//������Ե�㷨
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
