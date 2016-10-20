package com.mygdx.control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.actor.BaseActor;
import com.mygdx.world.World;

public class MoveControl implements IControl{
	
	public static final int STATE_WAIT=0;
	public static final int STATE_LEFT=1;
	public static final int STATE_RIGHT=2;
	public static final int STATE_JUMP=3;
	
	public static final int SPEED_0=0;
	public static final int SPEED_1=8;

	private BaseActor actor;//��Ҫһ����ɫ
	

	//----------------------
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	//----------------------

	
	/**
	 * ��˳ 2016��10��15��13:45:00
	 * <br>�ƶ�������
	 */
	public MoveControl(BaseActor actor) {
		setActor(actor);
		initialize();
	}
	
	
	
	public void initialize() {
		
	}
	
	
	public void update() {
		switch (actor.getState()) {
		case STATE_LEFT:
			actor.setX(actor.getX()-actor.getSpeed());
			break;
		case STATE_RIGHT:
			actor.setX(actor.getX()+actor.getSpeed());
			break;
		case STATE_JUMP:
			actor.setY(actor.getY()-actor.getFrameDuration()*World.GRAVITY*World.GRAVITY);
			break;
		default:
			break;
		}
		actor.getWorld().updatePosition();
	}
	
	
	
	
}
