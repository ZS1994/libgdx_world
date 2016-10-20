package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MySkin extends Skin{

	public MySkin() {
		initialize();
	}
	
	public void initialize() {
		add("翌華", new Texture("map/caoDi.png"));
		add("阨", new Texture("map/shui.png"));
		add("攷", new Texture("map/tree.png"));
		add("芩華", new Texture("map/tuDi.png"));
		add("翋褒", new TextureAtlas("man/man.atlas"));
		add("偌聽匼第", new Texture("data/button.png"));
		
		add("偌聽匼第", new Texture("data/button.png"));
		add("偌聽匼第", new Texture("data/button.png"));
		
	}
	
	
	
}
