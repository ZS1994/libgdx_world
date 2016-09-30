package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;

public class MyGdxGame extends ApplicationAdapter {
	
	
	Hero h;
	//-------------Map-----------------
	OrthographicCamera cam;
	SpriteBatch batch;
	TiledMap map;
	OrthogonalTiledMapRenderer OTRender;
	Stage stage;
	//-----FPS------
	LabelStyle labelStyle;
	BitmapFont font;
	int fps=0;
	Label labelfps;
	//-----------hero--------
	float x,y;
	//------------��ײ���---------
	private static int[][] barriers;
	public static final int MAP_TILE_WIDTH = 32;
	public static final int MAP_TILE_HEIGHT = 32;
	
	public static final int MAP_WIDTH_INDEX = 300;
	public static final int MAP_HEIGHT_INDEX = 200;
	
	
	@Override
	public void create () {
		//---------------MAP-------------
		map=new TmxMapLoader().load("data/map_1.tmx");
		cam=new OrthographicCamera();
		OTRender=new OrthogonalTiledMapRenderer(map);
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		barriers = new int [MAP_HEIGHT_INDEX][MAP_WIDTH_INDEX];
		initCell(map);
		//------------HERO---------------------
		stage=new Stage();
		h=new Hero(96, 96);
		stage.addActor(h);
		stage.addActor(h.btnL);
		stage.addActor(h.btnR);
		stage.addActor(h.btnU);
		stage.addActor(h.btnD);
		//-----------FPS----------
		font=new BitmapFont(Gdx.files.internal("font/font.fnt"),Gdx.files.internal("font/font.png"),false);
		labelStyle=new LabelStyle(font, font.getColor());
		labelfps=new Label("FPS:", labelStyle);
		labelfps.setPosition(0, Gdx.graphics.getHeight()-100);
		stage.addActor(labelfps);
		Gdx.input.setInputProcessor(stage);
//		tellBarriers();
	}
 
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updatePosition();
		cam.update();
		OTRender.setView(cam);
		OTRender.render();
		
//		tellHero(h);
		
		//---------��̨����-------------
		stage.draw();
		//-----------FPS����-------------
		int tempFps=(int) (1/Gdx.graphics.getDeltaTime());
		if (Math.abs(fps-tempFps)>3) {
			fps=tempFps;
		}
		labelfps.setText("FPS:"+fps);
	}
	
	
	/**
	 * ���ø��ֽ���
	 */
	private void updatePosition() {
		cam.position.x=h.getX();
		cam.position.y=h.getY();
		stage.getCamera().position.x=h.getX();
		stage.getCamera().position.y=h.getY();
		//����λ�õø�Ϊ��������λ��
		//�õ����ĵ��λ��
		float x=h.getX()-750;
		float y=h.getY()-350;
		h.btnU.setPosition(x, y+150);
		h.btnD.setPosition(x, y-150);
		h.btnL.setPosition(x-150, y);
		h.btnR.setPosition(x+150, y);
		//���ø���FPS��λ��
		x=h.getX()-900;
		y=h.getY()+450;
		labelfps.setPosition(x, y);
	}
	
	
	public void initCell(TiledMap map) {
       MapLayers layers = map.getLayers();
       for(MapLayer layer : layers) {
           if(layer.getName().equals("kc1")) {
               MapObjects objs = layer.getObjects();
               for(MapObject obj : objs ){
                   RectangleMapObject ro = (RectangleMapObject) obj;
                   if(ro.getName().equals("mario")) {
                       x = ro.getRectangle().x ;
                       y = ro.getRectangle().y;        
                   }
               }
           }else if(layer.getName().equals("obj")){
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;                    
                   //jΪ�ߣ��У� iΪ���У�
                   for(int j =0 ;j<tileLayer.getHeight();j++){
                       for(int i=0;i < tileLayer.getWidth();i++){
                           //getCell(�У���) �����귭ת
                           Cell cell = tileLayer.getCell( i, j );
                           if(cell!=null){                                
                               barriers[j][i] = 1;                            
                           }else {
                        	   barriers[j][i] = 0;
                           }
                       }
                   }        
               }
           }
       }
   }
	
	
	public static boolean passEnble(float x,float y){
		return barriers[(int)y/MAP_TILE_HEIGHT][(int)x/MAP_TILE_WIDTH]==0;
	}
	
	
	private void tellHero(Hero hero) {
		int x=(int)((hero.getX())/MAP_TILE_WIDTH);
		int y=(int)((hero.getY())/MAP_TILE_HEIGHT);
		System.out.println("xInd:"+x+"   yInd:"+y+"     ��λ��־:barriers["+y+"]["+x+"]"+barriers[y][x]);
	}
	
	private void tellBarriers() {
		for (int i = 0; i < barriers.length; i++) {
			for (int j = 0; j < barriers[i].length; j++) {
				System.out.print(barriers[i][j]);
			}
			System.out.println();
		}
		System.out.println(barriers[11][6]);
	}
	
	
}
