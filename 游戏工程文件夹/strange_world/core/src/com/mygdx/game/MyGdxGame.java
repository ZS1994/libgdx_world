package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.control.IControl;
import com.mygdx.control.MoveControl;
import com.mygdx.world.World;

public class MyGdxGame extends ApplicationAdapter {
	
	World world;
//	SpriteBatch batch;
	
	@Override
	public void create () {
		world=new World();
//		batch=new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.draw();
		/*
		batch.begin();
		batch.draw(World.skin.get("°´Å¥ËØ²Ä", Texture.class), 0, 0);
		batch.end();
		*/
	}
	
	@Override
	public void dispose () {
	}
}
