package com.mygdx.actor;

import jdk.internal.org.objectweb.asm.Handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.world.World;

public class Fps extends Actor{
	//-----FPS------
	private LabelStyle labelStyle;
	private BitmapFont font;
	private int fps=0;
	private Label labelfps;
	//---------------
	private BaseActor actor;
	
	
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}

	
	public Fps(BaseActor actor,Stage stage) {
		setActor(actor);
		//-----------FPS----------
		font=new BitmapFont(Gdx.files.internal("font/font.fnt"),Gdx.files.internal("font/font.png"),false);
		labelStyle=new LabelStyle(font, font.getColor());
		labelfps=new Label("FPS:", labelStyle);
		stage.addActor(labelfps);
	}
	
	/**
	 * 张顺 2016年10月15日16:18:04
	 * <br>调整位置，位置为相对位置
	 */
	public void update() {
		//还得改下FPS的位置
		float x=getActor().getX()-900;
		float y=getActor().getY()+450;
		labelfps.setPosition(x, y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		update();
		//-----------FPS绘制-------------
		int tempFps=(int) (1/Gdx.graphics.getDeltaTime());
		if (Math.abs(fps-tempFps)>3) {
			fps=tempFps;
		}
		labelfps.setText("FPS:"+fps);
	}
}
