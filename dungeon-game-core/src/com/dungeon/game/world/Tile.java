package com.dungeon.game.world;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
	public static final int TS = 32;
	public int id;
	public int data;
	public TextureRegion texture;
	private static HashMap<Integer, Integer> tileData;
	public Tile(TextureRegion[] spritesheet, int id) {
		this.id = id;
		data = id==1 ? 1:0;
		
		texture = spritesheet[id];
	}
	
	private static void initiateTileData(){
		tileData = new HashMap<Integer, Integer>();
		tileData.put(0,0);
	}
}
