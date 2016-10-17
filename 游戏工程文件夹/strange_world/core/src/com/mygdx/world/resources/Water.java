package com.mygdx.world.resources;

import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**��˳ 2016-10-17
 * ˮ�ز�
 * @author it023
 */
public class Water {
	private static Cell[][] cells;//�����cell����
	private static int width;
	private static int height;
	private static TiledMapTileLayer layer; 
	
	
	
	public static Cell[][] getCells() {
		return cells;
	}
	public static void setCells(Cell[][] cells) {
		Water.cells = cells;
	}
	public static int getWidth() {
		return width;
	}
	public static void setWidth(int width) {
		Water.width = width;
	}
	public static int getHeight() {
		return height;
	}
	public static void setHeight(int height) {
		Water.height = height;
	}
	public static TiledMapTileLayer getLayer() {
		return layer;
	}
	public static void setLayer(TiledMapTileLayer layer) {
		Water.layer = layer;
	}

	/**
	 * ��ʼ����Դ
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
	 * ��ͼ������ӣ��Լ��ͻ���ʾ��
	 * <br>�������ײ���
	 * <br>�����Դ��ڵ��໥����
	 * <br>���������
	 */
	public static void add(int xIndex,int yIndex) {
		//i�Ǹߡ�j�ǿ�
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				layer.setCell(xIndex, yIndex, cells[i][j]);
			}
		}
	}
	
	
	/**
	 * ��ͼ����ȥ�����Լ��ͻ���ʾ��
	 * <br>��ɾ����ײ���
	 * <br>���������
	 */
	public static void remove(int xIndex,int yIndex) {
		//i�Ǹߡ�j�ǿ�
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				layer.setCell(xIndex, yIndex, null);
			}
		}
	}
	
	
	
}
