package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.dungeon.game.textures.proceduralTexture;

public abstract class proceduralTile extends proceduralTexture {
	
	public int seed;
	
	public int x;
	
	public int y;

	public proceduralTile(int[] args) {
		super(32, 32, args);
		// TODO Auto-generated constructor stub
	}
	
	protected Random getRandomFromSeedAndCords(int seed, int x, int y){
		return new Random((long) (1f+((float)seed)*Math.cos(x)*Math.sin(y)));
	}

}
