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
		add("�ݵ�", new Texture("map/caoDi.png"));
		add("ˮ", new Texture("map/shui.png"));
		add("��", new Texture("map/tree.png"));
		add("����", new Texture("map/tuDi.png"));
		add("����", new TextureAtlas("man/man.atlas"));
		add("��ť�ز�", new Texture("data/button.png"));
		
		add("��ť�ز�", new Texture("data/button.png"));
		add("��ť�ز�", new Texture("data/button.png"));
		
	}
	
	
	
}
