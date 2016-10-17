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
	 * ��˳ 2016��10��15��16:18:04
	 * <br>����λ�ã�λ��Ϊ���λ��
	 */
	public void update() {
		//���ø���FPS��λ��
		float x=getActor().getX()-900;
		float y=getActor().getY()+450;
		labelfps.setPosition(x, y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		update();
		//-----------FPS����-------------
		int tempFps=(int) (1/Gdx.graphics.getDeltaTime());
		if (Math.abs(fps-tempFps)>3) {
			fps=tempFps;
		}
		labelfps.setText("FPS:"+fps);
	}
}
