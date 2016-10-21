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
	
	private World world;
	public static final float TIME_INTERVAL=0.2f;//这个游戏的时间间隔
	
	@Override
	public void create () {
		world=new World();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.draw();
	}
	
	@Override
	public void dispose () {
	}
}
