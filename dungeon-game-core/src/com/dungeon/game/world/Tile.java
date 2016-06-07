package com.dungeon.game.world;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
	public static final int TS = 32;
	public int id;
	public int data;
	public boolean flip;
	public int rotation;
	public Texture[] textures;
	public static final int[] SOLIDS = new int[]{1,10,11,12,13,14,15};
	
	public Tile(Texture[][] spritesheet, int id) {
		this.id = id;
		data = (isSolid(id)) ? 1:0;
		rotation = 0;
		
		textures = spritesheet[id];
	}
	
	public Tile(Texture[][] spritesheet, int id, int rotation, boolean flip) {
		this.id = id;
		data = (isSolid(id)) ? 1:0;
		
		this.rotation = rotation;
		this.flip = flip;
		
		textures = spritesheet[id];
	}
	
	public static boolean isSolid(int id){
		for(int i = 0; i < SOLIDS.length; i++) if(SOLIDS[i] == id) return true;
		return false;
	}
}
