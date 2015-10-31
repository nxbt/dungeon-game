package com.dungeon.game.tilemap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
	public static final int TS = 32;
	protected int id;
	protected int data;
	public TextureRegion texture;
	
	public Tile(Texture texmap, int id) {
		this.id = id;
		data = 0;
		TextureRegion[][] tmp = TextureRegion.split(texmap, texmap.getWidth()/TS, texmap.getHeight()/TS);
		texture =  (new TextureRegion[texmap.getWidth()/TS * texmap.getHeight()/TS])[id];
	}
}
