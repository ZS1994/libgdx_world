package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.actor.Hero;
import com.mygdx.entity.Barrier;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;

public class MyGdxGame extends ApplicationAdapter {
	
	
	Hero h;
	//-------------Map-----------------
	OrthographicCamera cam;
	SpriteBatch batch;
	TiledMap map;
	OrthogonalTiledMapRenderer OTRender;
	Stage stage;
	//------���ƻ�ͼ�㼯��+ͼ���ز�--------
	public static Map<String, TiledMapTileLayer> mapLayers=new HashMap<String, TiledMapTileLayer>();
	public static Map<String, Cell> mapCells=new HashMap<String, TiledMapTileLayer.Cell>();
	//-----FPS------
	LabelStyle labelStyle;
	BitmapFont font;
	int fps=0;
	Label labelfps;
	//-----------hero--------
	float x,y;
	//------------��ײ���---------
	public static final int MAP_TILE_WIDTH = 32;
	public static final int MAP_TILE_HEIGHT = 32;
	public static final int MAP_WIDTH_INDEX = 300;
	public static final int MAP_HEIGHT_INDEX = 200;
	public static Barrier[][] barriers = new Barrier[MAP_HEIGHT_INDEX][MAP_WIDTH_INDEX];
	//--------��־---------------------
//	Logger logger=Logger.getLogger(MyGdxGame.class);
	
	@Override
	public void create () {
		//---------------MAP-------------
		map=new TmxMapLoader().load("data/map_1.tmx");
		cam=new OrthographicCamera();
		OTRender=new OrthogonalTiledMapRenderer(map);
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		initCell(map);
		//------------HERO---------------------
		stage=new Stage(new ScalingViewport(Scaling.stretch, 1920, 1080,cam));
		h=new Hero(96, 96);
		stage.addActor(h);
		stage.addActor(h.btnL);
		stage.addActor(h.btnR);
		stage.addActor(h.btnU);
		stage.addActor(h.btnD);
		stage.addActor(h.btn_A);
		//-----------FPS----------
		font=new BitmapFont(Gdx.files.internal("font/font.fnt"),Gdx.files.internal("font/font.png"),false);
		labelStyle=new LabelStyle(font, font.getColor());
		labelfps=new Label("FPS:", labelStyle);
		labelfps.setPosition(0, Gdx.graphics.getHeight()-100);
		stage.addActor(labelfps);
		Gdx.input.setInputProcessor(stage);
		
		
//		tellBarriers();
		System.out.println(barriers[0][0].getType());
	}
 
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updatePosition();
		cam.update();
		OTRender.setView(cam);
		OTRender.render();
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
		h.btn_A.setPosition(x+1500, y);
		//���ø���FPS��λ��
		x=h.getX()-900;
		y=h.getY()+450;
		labelfps.setPosition(x, y);
	}
	
	
	public void initCell(TiledMap map) {
       MapLayers layers = map.getLayers();
       for(MapLayer layer : layers) {
    	   /**
    	    * ��ûд�ã���ʱ������
    	    */
           if(layer.getName().equals("kc1")) {
               MapObjects objs = layer.getObjects();
               for(MapObject obj : objs ){
                   RectangleMapObject ro = (RectangleMapObject) obj;
                   if(ro.getName().equals("mario")) {
                       x = ro.getRectangle().x ;
                       y = ro.getRectangle().y;        
                   }
               }
           }
           //�����ǲ����ƻ��ϰ�����ײ���
           else if(layer.getName().equals("obstacle_undestroy")){
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;   
                   mapLayers.put("obstacle_undestroy",tileLayer);
                   /**
                    *�����ֶ�����Cell������ʵ�ֵ�ͼ�ƻ�Ч������ͼ��̬�仯��
                    *�ѳɹ�
                    *����Ҫ�о�
            	   tileLayer.setCell(1, 1, null);
            	   tileLayer.setCell(2, 1, null);
            	   tileLayer.setCell(3, 1, null);
            	   tileLayer.setCell(4, 1, null);
                    */
                   //jΪ�ߣ��У� iΪ���У�
                   for(int j =0 ;j<tileLayer.getHeight();j++){
                       for(int i=0;i < tileLayer.getWidth();i++){
                           //getCell(�У���) �����귭ת
                           Cell cell = tileLayer.getCell( i, j );
                           if (barriers[j][i]==null) {
                        	   barriers[j][i]=new Barrier();
                           }
                           if(cell!=null){  
                        	   barriers[j][i].setBarrier(Barrier.BARRIER_PASS_NO);
                        	   barriers[j][i].setType(Barrier.TYPE_UNDESTROY);
                           }else {
                        	   barriers[j][i].setBarrier(Barrier.BARRIER_PASS_YES);
                           }
                       }
                   }        
               }
           }
         //���ƻ��ϰ���������ײ���
           else if(layer.getName().equals("obstacle_destroy_tree")){
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;    
                   mapLayers.put("obstacle_destroy_tree",tileLayer);
                   //jΪ�ߣ��У� iΪ���У�
                   for(int j =0 ;j<tileLayer.getHeight();j++){
                       for(int i=0;i < tileLayer.getWidth();i++){
                           //getCell(�У���) �����귭ת
                           Cell cell = tileLayer.getCell( i, j );
                           if (barriers[j][i].getBarrier()==Barrier.BARRIER_PASS_YES) {
                        	   if(cell!=null){                                
                        		   barriers[j][i].setBarrier(Barrier.BARRIER_PASS_NO);    
                        		   barriers[j][i].setType(Barrier.TYPE_DESTROY_TREE);
                        	   }else {
                        		   barriers[j][i].setBarrier(Barrier.BARRIER_PASS_YES);
                        	   }
                           }
                       }
                   }        
               }
           }
           /*
            */
           else if(layer.getName().equals("obstacle_destroy_shelter")){
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;    
                   //jΪ�ߣ��У� iΪ���У�
                   mapCells.put("�ݵ�", tileLayer.getCell(0, 0));
                   mapCells.put("ʯ��", tileLayer.getCell(1, 0));
                   mapCells.put("ˮ", tileLayer.getCell(2, 0));
                   mapCells.put("��", tileLayer.getCell(3, 0));
                   mapCells.put("����", tileLayer.getCell(4, 0));
                   mapCells.put("��ľ", tileLayer.getCell(6, 0));
               }
           }
           
           //δ�����...
       }
   }
	
	/**
	 * �����ƻ��ϰ�����ײ���
	 */
	public static boolean passEnble(float x,float y){
		return barriers[(int)y/MAP_TILE_HEIGHT][(int)x/MAP_TILE_WIDTH].getBarrier()==Barrier.BARRIER_PASS_YES;
	}
	
	public static String queryTypeFromBarrier(float x,float y){
		return barriers[(int)y/MAP_TILE_HEIGHT][(int)x/MAP_TILE_WIDTH].getType();
	}
	
	
	
	
	/**
	 * ��ӡ����λ�ã����ڲ��ԣ����ѻ�������
	 * @param hero
	 */
	@SuppressWarnings("unused")
	private void tellHero(Hero hero) {
		int x=(int)((hero.getX())/MAP_TILE_WIDTH);
		int y=(int)((hero.getY())/MAP_TILE_HEIGHT);
		System.out.println("xInd:"+x+"   yInd:"+y+"     ��λ��־:barriers["+y+"]["+x+"]"+barriers[y][x].getBarrier());
	}
	
	
	
	
	/**
	 * ��ӡ��ײ��������ͼ
	 * ��ȡ��50*50�Ŀ���
	 */
	@SuppressWarnings("unused")
	private void tellBarriers() {
		for (int i = 0; i < 50; i++) {
			if (i==0) {
				for (int j = 0; j < 50; j++) {
				    if (j==0) {
				    	System.out.print("   ");
					}else if (j<11) {
						System.out.print((j-1)+"  ");
					}else if(j>=11 && j<101){
						System.out.print((j-1)+" ");
					}
				}
			}else {
				for (int j = 0; j < 50; j++) {
					if (j==0) {
						if (i<11) {
							System.out.print((i-1)+"  ");
						}else if(i>=11 && i<101){
							System.out.print((i-1)+" ");
						}
					}else {
						if (j<10) {
							System.out.print(barriers[i-1][j-1].getBarrier()+"  ");
						}else if(j>=10 && j<100){
							System.out.print(barriers[i-1][j-1].getBarrier()+"  ");
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	
}
