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
	public static final int STATE_UP=3;
	public static final int STATE_DOWN=4;
	
	public static final int SPEED_0=0;
	public static final int SPEED_1=8;

	private BaseActor actor;//需要一个角色
	

	//----------------------
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	//----------------------

	
	/**
	 * 张顺 2016年10月15日13:45:00
	 * <br>移动控制器
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
		case STATE_UP:
			actor.setY(actor.getY()+actor.getSpeed());
			break;
		case STATE_DOWN:
			actor.setY(actor.getY()-actor.getSpeed());
			break;
		default:
			break;
		}
		actor.getWorld().updatePosition();
	}
	
	
	
	
}
