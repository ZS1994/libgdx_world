package com.mygdx.world.resources;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Mountain {
	
	private static Cell[][] cells;//所需的cell容器
	private static TiledMapTileLayer layer; 
	

	
	
	public static Cell[][] getCells() {
		return cells;
	}
	public static void setCells(Cell[][] cells) {
		Mountain.cells = cells;
	}
	public static TiledMapTileLayer getLayer() {
		return layer;
	}
	public static void setLayer(TiledMapTileLayer layer) {
		Mountain.layer = layer;
	}


	/**
	 * 在图层上添加（自己就会显示）
	 * <br>并添加碰撞检测
	 * <br>会与以存在的相互覆盖
	 * <br>起点是左下
	 */
	public static void add(int xIndex,int yIndex) {
		//i是高。j是宽
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				layer.setCell(xIndex+i, yIndex+j, cells[i][j]);
			}
		}
	}
	
	
	/**
	 * 在图层上去掉（自己就会显示）
	 * <br>并删除碰撞检测
	 * <br>起点是左下
	 */
	public static void remove(int xIndex,int yIndex) {
		//i是高。j是宽
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				layer.setCell(xIndex+i, yIndex+j, null);
			}
		}
	}
	
}
