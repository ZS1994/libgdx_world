package com.mygdx.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.actor.BaseActor;
import com.mygdx.actor.Fps;
import com.mygdx.actor.MainActor;
import com.mygdx.control.AnimationControl;
import com.mygdx.control.ButtonControl;
import com.mygdx.control.IControl;
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
		initCell(map);
		//--------------------
		h=new MainActor(100,100,this);
		//-----------------
		stage.addActor(h);
		h.initialize("man/man.atlas");
		h.getControls().add(new AnimationControl(h));
		h.getControls().add(new ButtonControl(h));
		h.getControls().add(new MoveControl(h));
		//----------fps----------
		fps=new Fps(h,stage);
		stage.addActor(fps);
		Gdx.input.setInputProcessor(stage);
	}
	
	public void initCell(TiledMap map) {
       MapLayers layers = map.getLayers();
       for(MapLayer layer : layers) {
       }
   }
	
	public void updatePosition() {
		cam.position.x=h.getX();
		cam.position.y=h.getY();
		stage.getCamera().position.x=h.getX();
		stage.getCamera().position.y=h.getY();
		System.out.print("【cam.position.x】:");
		System.out.print(cam.position.x);
		System.out.print("【stage.getCamera().position.x】:");
		System.out.print(stage.getCamera().position.x);
		System.out.print("【x】:");
		System.out.print(h.getX());
		System.out.print("【cam.position.y】:");
		System.out.print(cam.position.y);
		System.out.print("【stage.getCamera().position.y】:");
		System.out.print(stage.getCamera().position.y);
		System.out.print("【y】:");
		System.out.print(h.getY());
		System.out.println();
	}
	
	public void draw() {
		cam.update();
		OTRender.setView(cam);
		OTRender.render();
		//---------舞台绘制-------------
		stage.draw();
	}
	
	
}	
