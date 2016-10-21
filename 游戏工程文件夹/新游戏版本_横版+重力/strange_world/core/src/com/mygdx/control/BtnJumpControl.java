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

public class BtnJumpControl implements IControl{

	private BaseActor actor;
	private ImageButton btnA;
	
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}

	public BtnJumpControl(BaseActor actor) {
		setActor(actor);
		initialize();
	}
	
	public void initialize() {
		TextureRegion[][] regions= TextureRegion.split(World.skin.get("按钮素材", Texture.class), 120, 120) ;
		regions[1][2].flip(false, true);
		regions[1][3].flip(false, true);
		btnA=new ImageButton(new TextureRegionDrawable(regions[1][2]), new TextureRegionDrawable(regions[1][3]));
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
		float xtmp=getActor().getX()+500;
		float ytmp=getActor().getY()-300;
		btnA.setPosition(xtmp+200, ytmp+100);
	}
	
	
	
	
}
