package com.mygdx.control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.actor.BaseActor;
import com.mygdx.world.World;

public class BtnDirectionControl implements IControl{

	private BaseActor actor;
	private ImageButton btnL,btnR,btnU,btnD;
	
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}

	public BtnDirectionControl(BaseActor actor) {
		setActor(actor);
		initialize();
	}
	
	public void initialize() {
		TextureRegion[][] regions1= TextureRegion.split(World.skin.get("按钮素材", Texture.class), 120, 120) ;
		TextureRegion[][] regions2= TextureRegion.split(World.skin.get("按钮素材", Texture.class), 120, 120) ;
		for (int j = 0; j < regions2[0].length; j++) {
			regions2[0][j].flip(true, false);
		}
		for (int j = 0; j < regions2[1].length; j++) {
			regions2[1][j].flip(false, true);
		}
		btnL=new ImageButton(new TextureRegionDrawable(regions1[0][0]), new TextureRegionDrawable(regions1[0][1]));
		btnR=new ImageButton(new TextureRegionDrawable(regions2[0][0]), new TextureRegionDrawable(regions2[0][1]));
		btnD=new ImageButton(new TextureRegionDrawable(regions1[1][2]), new TextureRegionDrawable(regions1[1][3]));
		btnU=new ImageButton(new TextureRegionDrawable(regions2[1][2]), new TextureRegionDrawable(regions2[1][3]));
		getActor().getStage().addActor(btnL);
		getActor().getStage().addActor(btnR);
		getActor().getStage().addActor(btnD);
		getActor().getStage().addActor(btnU);
		//---------添加监听---------------
		btnL.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				getActor().setSpeed(MoveControl.SPEED_0);
				getActor().setState(MoveControl.STATE_WAIT);
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				getActor().setState(MoveControl.STATE_LEFT);
				getActor().setStateLast(MoveControl.STATE_LEFT);
				getActor().setSpeed(MoveControl.SPEED_1);
				return true;
			}
		});
		btnR.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				getActor().setSpeed(MoveControl.SPEED_0);
				getActor().setState(MoveControl.STATE_WAIT);
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				getActor().setState(MoveControl.STATE_RIGHT);
				getActor().setStateLast(MoveControl.STATE_RIGHT);
				getActor().setSpeed(MoveControl.SPEED_1);
				return true;
			}
		});
		btnD.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		btnU.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (!actor.isJump()) {
					actor.setSpeedy(100);
				}
				return true;
			}
		});
	}
	
	@Override
	public void update() {
		//--------设置成相对位置-----------------
		float xtmp=getActor().getX()-700;
		float ytmp=getActor().getY()-300;
		btnL.setPosition(xtmp-150, ytmp);
		btnR.setPosition(xtmp+150, ytmp);
		btnD.setPosition(xtmp, ytmp-150);
		btnU.setPosition(xtmp, ytmp+150);
	}
	
	
	
	
}
