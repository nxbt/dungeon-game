package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.dungeon.game.textures.ProceduralTexture;

public abstract class ProceduralTile extends ProceduralTexture {
	
	public int seed;
	
	public int x;
	
	public int y;

	public ProceduralTile(Object[] args) {
		super(32, 32, args);
		// TODO Auto-generated constructor stub
	}

}
