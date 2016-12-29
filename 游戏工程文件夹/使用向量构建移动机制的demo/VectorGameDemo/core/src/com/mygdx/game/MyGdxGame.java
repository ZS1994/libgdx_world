package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.actor.BaseActor;

public class MyGdxGame extends ApplicationAdapter {
	
	Stage stage;
	BaseActor ba;
	
	
	@Override
	public void create () {
		OrthographicCamera cam=new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage=new Stage(new ScalingViewport(Scaling.stretch, 1920, 1080,cam));
		ba=new BaseActor(0, 0);
		stage.addActor(ba);
		stage.addActor(ba.btnL);
		stage.addActor(ba.btnR);
		stage.addActor(ba.btnD);
		stage.addActor(ba.btnU);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	@Override
	public void dispose () {
		ba.clear();
		stage.dispose();
	}
}
