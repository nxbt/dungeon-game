package com.dungeon.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
	public static final int TS = 32;
	public int id;
	public int data;
	public TextureRegion texture;
	
	public Tile(TextureRegion[] spritesheet, int id) {
		this.id = id;
		data = id==2 ? 1:0;
		
		texture = spritesheet[id];
	}
}
