package com.dungeon.game.world;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dungeon.game.spritesheet.Spritesheet;

public class Tile {
	public static final int TS = 32;
	public int id;
	public int data;
	public int rotation;
	public Texture[] textures;
	
	public Tile(Texture[][] spritesheet, int id) {
		this.id = id;
		data = (id==1||id==10||id==11||id==12) ? 1:0;
		rotation = 0;
		
		textures = spritesheet[id];
	}
	
	public Tile(Texture[][] spritesheet, int id, int rotation) {
		this.id = id;
		data = (id==1||id==10||id==11||id==12) ? 1:0;
		this.rotation = rotation;
		
		textures = spritesheet[id];
	}
}
