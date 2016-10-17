package com.mygdx.tools;

import com.mygdx.world.TiledMapSystem;

public class Transform {
	
	public static int xToxIndex(float x) {
		return (int)x/TiledMapSystem.MAP_TILE_WIDTH;
	}
	
	
}
