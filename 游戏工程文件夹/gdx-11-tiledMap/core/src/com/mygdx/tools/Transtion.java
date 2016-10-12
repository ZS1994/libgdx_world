package com.mygdx.tools;

import com.mygdx.game.MyGdxGame;

public class Transtion {
	
	public static int xToxIndex(float x) {
		return (int)x/MyGdxGame.MAP_TILE_WIDTH;
	}
	
}
