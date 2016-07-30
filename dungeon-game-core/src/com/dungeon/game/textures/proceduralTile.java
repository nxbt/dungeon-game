package com.dungeon.game.textures;

public abstract class proceduralTile extends proceduralTexture {
	
	public int seed;
	
	public int x;
	
	public int y;

	public proceduralTile(int seed, int x, int y, int[] args) {
		super(32, 32, args);
		// TODO Auto-generated constructor stub
	}

}
