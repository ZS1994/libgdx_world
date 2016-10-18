package com.mygdx.test;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.world.TiledMapSystem;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		/**
		List<Integer> list=new ArrayList<Integer>();
		
		list.add(4);
		list.add(2);
		list.add(2);
		list.add(2);
		list.add(5);
		list.add(3);
		list.add(5);
		
		
		for (int i = list.size()-1; i >0 ; i--) {
			if (list.get(i)>=list.get(i-1)) {
				list.remove(i);
			}else {
				list.remove(i-1);
			}
		}
		
		
		System.out.println(list.toString());
		 * 测试找最小数：成功(寻路算法时)
		 */
		List<Integer> list=new ArrayList<Integer>();
		List<Integer> list2=new ArrayList<Integer>();
		
		list.add(4);
		list.add(2);
		list.add(2);
		list.add(2);
		list.add(5);
		list.add(3);
		list.add(5);
		
		list2.add(2);
		list2.add(3);
		list2.add(5);
		
		for (int i = list.size()-1; i >=0 ; i--) {
			for (int j = 0; j < list2.size(); j++) {
				if (list.get(i)==list2.get(j)) {
					list.remove(i);
					break;
				}
			}
		}
		
		
		System.out.println(list.toString());
		/*
		 * 测试找最小数：成功(寻路算法时)
		 */
	}

}
