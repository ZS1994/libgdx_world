package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.actor.Fps;
import com.mygdx.actor.MainActor;
import com.mygdx.control.AnimationControl;
import com.mygdx.control.ButtonControl;
import com.mygdx.control.CollisionControl;
import com.mygdx.control.MoveControl;
import com.mygdx.game.MySkin;

public class World {
	
	//-------------Map-----------------
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private TiledMap map;
	private OrthogonalTiledMapRenderer OTRender;
	private Stage stage;
	//-----------------------
	private MainActor h;
	public static MySkin skin;
	static{
		skin=new MySkin();
	}
	//----FPS------------
	private Fps fps;
	//-----��ͼ����ײ���--------------
	
	
	
	/**
	 * ���� 2016��10��15��14:05:47
	 * ��˳ ������ͼ
	 */
	public World() {
		//---------------MAP-------------
		map=new TmxMapLoader().load("map/map_2.tmx");
		cam=new OrthographicCamera();
		OTRender=new OrthogonalTiledMapRenderer(map);
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage=new Stage(new ScalingViewport(Scaling.stretch, 1920, 1080,cam));
		//--------------------
		h=new MainActor(150,200,45,94,this);
		//-----------------
		stage.addActor(h);
		h.initialize("man/man.atlas");
		h.getControls().add(new AnimationControl(h));
		h.getControls().add(new ButtonControl(h));
		h.getControls().add(new CollisionControl(h));
		h.getControls().add(new MoveControl(h));
		//----------fps----------
		fps=new Fps(h,stage);
		stage.addActor(fps);
		Gdx.input.setInputProcessor(stage);
		//---------��ϵͳ��ʼ��---------
		TiledMapSystem.initialize(map);
	}
	
	
	public void updatePosition() {
		cam.position.x=h.getX();
		cam.position.y=h.getY();
		stage.getCamera().position.x=h.getX();
		stage.getCamera().position.y=h.getY();
	}
	
	public void draw() {
		cam.update();
		OTRender.setView(cam);
		OTRender.render();
		//---------��̨����-------------
		updatePosition();
		stage.draw();
	}
	
	
}	
