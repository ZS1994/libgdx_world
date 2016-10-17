package com.mygdx.world;

import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**张顺 2016-10-17
 * 水素材
 * @author it023
 */
public class Water {
	private static Cell[][] cells;//所需的cell容器
	private static int width;
	private static int height;
	private static TiledMapTileLayer layer; 
	
	
	/**
	 * 初始化资源
	 * @param cells
	 * @param width
	 * @param height
	 * @param layer
	 */
	public static void initialize(Cell[][] cells,int width,int height,TiledMapTileLayer layer) {
		Water.cells=cells;
		Water.width=width;
		Water.height=height;
		Water.layer=layer;
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
				layer.setCell(xIndex, yIndex, cells[i][j]);
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
				layer.setCell(xIndex, yIndex, null);
			}
		}
	}
	
	
	
}
