package com.mygdx.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.world.collision.Barrier;
import com.mygdx.world.resources.Water;

public class TiledMapSystem {
	//------------------------
	public static final int MAP_TILE_WIDTH = 32;
	public static final int MAP_TILE_HEIGHT = 32;
	public static final int MAP_WIDTH_INDEX = 300;
	public static final int MAP_HEIGHT_INDEX = 200;
	private static Barrier[][] barriers=new Barrier[MAP_HEIGHT_INDEX][MAP_WIDTH_INDEX];;
	
	
	public static void initialize(TiledMap map) {
		MapLayers layers = map.getLayers();
        for(MapLayer layer : layers) {
           //������ˮ
           if(layer.getName().equals("water")){
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;   
                   Water.setLayer(tileLayer);//ˮ�������ʼ��ͼ��
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
                        	   barriers[j][i].setType(Barrier.TYPE_WATER);
                           }else {
                        	   barriers[j][i].setBarrier(Barrier.BARRIER_PASS_YES);
                           }
                       }
                   }        
               }
           }
         //ɽ
           else if(layer.getName().equals("mountain")){//ɽ
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;    
                   //jΪ�ߣ��У� iΪ���У�
                   for(int j =0 ;j<tileLayer.getHeight();j++){
                       for(int i=0;i < tileLayer.getWidth();i++){
                           //getCell(�У���) �����귭ת
                           Cell cell = tileLayer.getCell( i, j );
                           if (barriers[j][i].getBarrier()==Barrier.BARRIER_PASS_YES) {
                        	   if(cell!=null){                                
                        		   barriers[j][i].setBarrier(Barrier.BARRIER_PASS_NO);    
                        		   barriers[j][i].setType(Barrier.TYPE_MOUNTAIN);
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
           else if(layer.getName().equals("material")){//��ʼ����ͼ�����
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;   
                   Cell[][] tmpCells1=new Cell[1][1];
                   tmpCells1[0][0]=tileLayer.getCell(0, 2);
                   Water.setCells(tmpCells1);
                   
                   //δ�����...
               }
           }
           //δ�����...
        }
	}
	
	
	/**
	 * ��ײ���
	 */
	public static boolean passEnble(float x,float y){
		return barriers[(int)y/MAP_TILE_HEIGHT][(int)x/MAP_TILE_WIDTH].getBarrier()==Barrier.BARRIER_PASS_YES;
	}
	
	
}
