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
import com.mygdx.actor.Dog;
import com.mygdx.actor.Fps;
import com.mygdx.actor.MainActor;
import com.mygdx.ai.Pathfind;
import com.mygdx.control.AnimationControl;
import com.mygdx.control.BtnAddControl;
import com.mygdx.control.BtnDeleteControl;
import com.mygdx.control.BtnDirectionControl;
import com.mygdx.control.CollisionControl;
import com.mygdx.control.FootPrintControl;
import com.mygdx.control.MoveControl;
import com.mygdx.game.MySkin;
import com.mygdx.world.resources.Mountain;
import com.mygdx.world.resources.Water;

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
	//-----地图的碰撞检测--------------
	//--------世界的重力------------------
//	public static float GRAVITY=9.8f;
	
	/**
	 * 世界 2016年10月15日14:05:47
	 * 张顺 包含地图
	 */
	public World() {
		//---------------MAP-------------
		map=new TmxMapLoader().load("map/map_2.tmx");
		cam=new OrthographicCamera();
		OTRender=new OrthogonalTiledMapRenderer(map);
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage=new Stage(new ScalingViewport(Scaling.stretch, 1920, 1080,cam));
		//---------各系统初始化---------
		//-----地图系统------
		TiledMapSystem.setMap(map);
		TiledMapSystem.initialize();
		//-------主角----------
		h=new MainActor(600,660,this);
		stage.addActor(h);
		h.getControls().add(new AnimationControl(h));
		h.getControls().add(new BtnDirectionControl(h));
		h.getControls().add(new BtnAddControl(h));
		h.getControls().add(new BtnDeleteControl(h));
		h.getControls().add(new CollisionControl(h));
		h.getControls().add(new MoveControl(h));
		//---------狗----------------
		addDogs();
		//----------fps----------
		fps=new Fps(h,stage);
		stage.addActor(fps);
		/*
		----------测试添加组件-----成功-------
		Mountain.add(6, 6);
		Water.add(7, 7);
		TiledMapSystem.initialize();
		*/
		Gdx.input.setInputProcessor(stage);
	}
	
	private void addDogs() {
		Dog dog=new Dog(1000, 1000, this);
		dog.setSpeed(1);
		stage.addActor(dog);
		dog.getControls().add(new AnimationControl(dog));
		dog.getControls().add(new Pathfind(dog, h));
		dog.getControls().add(new CollisionControl(dog));
		dog.getControls().add(new MoveControl(dog));
		dog.getControls().add(new FootPrintControl(dog));
		//-----------再来5条狗看看---------------------
		/*
		Dog dog1=new Dog(1000, 3000, this);
		stage.addActor(dog1);
		dog.getControls().add(new AnimationControl(dog1));
		dog.getControls().add(new Pathfind(dog1, h));
		dog.getControls().add(new CollisionControl(dog1));
		dog.getControls().add(new MoveControl(dog1));
		Dog dog2=new Dog(1000, 200, this);
		stage.addActor(dog2);
		dog.getControls().add(new AnimationControl(dog2));
		dog.getControls().add(new Pathfind(dog2, h));
		dog.getControls().add(new CollisionControl(dog2));
		dog.getControls().add(new MoveControl(dog2));
		Dog dog3=new Dog(600, 3500, this);
		stage.addActor(dog3);
		dog.getControls().add(new AnimationControl(dog3));
		dog.getControls().add(new Pathfind(dog3, h));
		dog.getControls().add(new CollisionControl(dog3));
		dog.getControls().add(new MoveControl(dog3));
		Dog dog4=new Dog(800, 3000, this);
		stage.addActor(dog4);
		dog.getControls().add(new AnimationControl(dog4));
		dog.getControls().add(new Pathfind(dog4, h));
		dog.getControls().add(new CollisionControl(dog4));
		dog.getControls().add(new MoveControl(dog4));
		Dog dog5=new Dog(4000, 3000, this);
		stage.addActor(dog5);
		dog.getControls().add(new AnimationControl(dog5));
		dog.getControls().add(new Pathfind(dog5, h));
		dog.getControls().add(new CollisionControl(dog5));
		dog.getControls().add(new MoveControl(dog5));
		*/
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
		//---------舞台绘制-------------
		updatePosition();
		stage.draw();
	}
	
	
}	
