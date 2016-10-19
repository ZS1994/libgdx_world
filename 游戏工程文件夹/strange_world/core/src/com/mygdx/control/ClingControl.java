package com.mygdx.control;

import com.mygdx.actor.BaseActor;
import com.mygdx.world.TiledMapSystem;

public class ClingControl implements IControl{
	
	private BaseActor actor;

	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	
	public ClingControl(BaseActor actor) {
		this.actor = actor;
	}
	
	@Override
	public void initialize() {
		
	}
	
	@Override
	public void update() {
		switch (actor.getState()) {
		case MoveControl.STATE_LEFT:
			//Ìù½ô±ßÔµËã·¨
			float x=((int) ((actor.getX()-actor.getSpeed())/TiledMapSystem.MAP_TILE_WIDTH))*TiledMapSystem.MAP_TILE_WIDTH;
			actor.setX(x);
			break;
		case MoveControl.STATE_RIGHT:
			
			break;

		case MoveControl.STATE_UP:
			
			break;
		case MoveControl.STATE_DOWN:
			
			break;
		default:
			break;
		}
	}
	
	
	
	
}
