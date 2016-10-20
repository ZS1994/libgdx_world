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
import com.mygdx.world.resources.Mountain;
import com.mygdx.world.resources.Water;

public class TiledMapSystem {
	//------------------------
	public static final int MAP_TILE_WIDTH = 32;
	public static final int MAP_TILE_HEIGHT = 32;
	public static final int MAP_WIDTH_INDEX = 300;
	public static final int MAP_HEIGHT_INDEX = 200;
	private static Barrier[][] barriers=new Barrier[MAP_HEIGHT_INDEX][MAP_WIDTH_INDEX];;
	private static TiledMap map;
	
	
	public static TiledMap getMap() {
		return map;
	}
	public static void setMap(TiledMap map) {
		TiledMapSystem.map = map;
	}


	public static void initialize() {
		MapLayers layers = map.getLayers();
        for(MapLayer layer : layers) {
           //首先是水
           if(layer.getName().equals("water")){
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;   
                   //水在这里初始化图层
                   Water.setLayer(tileLayer);
                   Cell[][] tmpCells=new Cell[1][1];
                   tmpCells[0][0]=tileLayer.getCell(0, 0);
                   Water.setCells(tmpCells);
                   //j为高（行） i为宽（列）
                   for(int j =0 ;j<tileLayer.getHeight();j++){
                       for(int i=0;i < tileLayer.getWidth();i++){
                           //getCell(列，行) 纵坐标翻转
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
         //山
           else if(layer.getName().equals("mountain")){//山
               if(layer instanceof TiledMapTileLayer){
                   TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;    
                   //山在这里初始化
                   Mountain.setLayer(tileLayer);
                   Cell[][] tmpCells=new Cell[1][1];
                   tmpCells[0][0]=tileLayer.getCell(0, 0);
                   Mountain.setCells(tmpCells);
                   //j为高（行） i为宽（列）
                   for(int j =0 ;j<tileLayer.getHeight();j++){
                       for(int i=0;i < tileLayer.getWidth();i++){
                           //getCell(列，行) 纵坐标翻转
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
           //未完待续...
        }
	}
	
	
	/**
	 * 碰撞检测
	 */
	public static boolean passEnble(float x,float y){
		return barriers[(int)y/MAP_TILE_HEIGHT][(int)x/MAP_TILE_WIDTH].getBarrier()==Barrier.BARRIER_PASS_YES;
	}
	
	
}
