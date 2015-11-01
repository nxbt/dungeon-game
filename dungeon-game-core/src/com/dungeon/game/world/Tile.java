package com.dungeon.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
	public static final int TS = 32;
	protected int id;
	protected int data;
	public TextureRegion texture;
	
	public Tile(TextureRegion[] spritesheet, int id) {
		this.id = id;
		data = 0;
		
		texture = spritesheet[id];
	}
}
