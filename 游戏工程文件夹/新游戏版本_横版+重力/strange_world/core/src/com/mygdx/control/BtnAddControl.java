package com.mygdx.control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.actor.BaseActor;
import com.mygdx.tools.Transform;
import com.mygdx.world.TiledMapSystem;
import com.mygdx.world.World;
import com.mygdx.world.resources.Mountain;

public class BtnAddControl implements IControl{

	private BaseActor actor;
	private ImageButton btnA;
	
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}

	public BtnAddControl(BaseActor actor) {
		setActor(actor);
		initialize();
	}
	
	public void initialize() {
		TextureRegion[][] regions= TextureRegion.split(World.skin.get("按钮素材", Texture.class), 120, 120) ;
		btnA=new ImageButton(new TextureRegionDrawable(regions[0][2]), new TextureRegionDrawable(regions[0][3]));
		getActor().getStage().addActor(btnA);
		//---------添加监听---------------
		btnA.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				/*
				switch (actor.getState()) {
				case MoveControl.STATE_LEFT:
					Mountain.add(Transform.xToxIndex(actor.getX())-1, Transform.xToxIndex(actor.getY()));
					break;
				case MoveControl.STATE_RIGHT:
					Mountain.add(Transform.xToxIndex(actor.getX()+actor.getWidth())+1, Transform.xToxIndex(actor.getY()));
					break;
				case MoveControl.STATE_UP:
					Mountain.add(Transform.xToxIndex(actor.getX()), Transform.xToxIndex(actor.getY()+actor.getHeight())+1);
					break;
				case MoveControl.STATE_DOWN:
					Mountain.add(Transform.xToxIndex(actor.getX()), Transform.xToxIndex(actor.getY())-1);
					break;
				default:
					break;
				}
				为什么不用？太反人类了，按方向键才能建
				*/
				int w=(int)actor.getWidth()/TiledMapSystem.MAP_TILE_WIDTH;
				int h=(int)actor.getHeight()/TiledMapSystem.MAP_TILE_HEIGHT;
				if (actor.getWidth()>w*TiledMapSystem.MAP_TILE_WIDTH) {
					w=w+1+2;
				}else {
					w=w+2;
				}
				if (actor.getHeight()>h*TiledMapSystem.MAP_TILE_HEIGHT) {
					h=h+1+1;
				}else {
					h=h+1;
				}
				for (int i = 0; i < w; i++) {
					Mountain.add(Transform.xToxIndex(actor.getX())-1+i, Transform.xToxIndex(actor.getY())-1);
					Mountain.add(Transform.xToxIndex(actor.getX())-1+i, Transform.xToxIndex(actor.getY())+h-2+1);
				}
				for (int j = 0; j < h; j++) {
					Mountain.add(Transform.xToxIndex(actor.getX())-1, Transform.xToxIndex(actor.getY())-1+j);
					Mountain.add(Transform.xToxIndex(actor.getX())-1+w-1, Transform.xToxIndex(actor.getY())-1+j);
				}
				TiledMapSystem.initialize();
				return true;
			}
		});
	}
	
	@Override
	public void update() {
		//--------设置成相对位置-----------------
		float xtmp=getActor().getX()+700;
		float ytmp=getActor().getY()-300;
		btnA.setPosition(xtmp, ytmp+150);
	}
	
	
	
	
}
