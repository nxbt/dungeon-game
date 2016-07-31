package com.dungeon.game.textures.tiles;

import java.util.Random;

import com.dungeon.game.textures.ProceduralTexture;

public abstract class ProceduralTile extends ProceduralTexture {
	
	public int seed;
	
	public int x;
	
	public int y;

	public ProceduralTile(int[] args) {
		super(32, 32, args);
		// TODO Auto-generated constructor stub
	}
	
	protected Random getRandomFromSeedAndCords(int seed, int x, int y){ //this is not very efficent...
		Random xRandomer = new Random(x);
		Random yRandomer = new Random(y);
		xRandomer.nextFloat();
		yRandomer.nextFloat();
		yRandomer.nextFloat();
		return new Random((long) (1f+((float)seed)*xRandomer.nextFloat()*yRandomer.nextFloat()));
	}
	
	protected Random getRandomFromSeedAndX(int seed, int x){
		Random xRandomer = new Random(x);
		xRandomer.nextFloat();
		return new Random((long) (1f+((float)seed)*xRandomer.nextFloat()));
	}
	
	protected Random getRandomFromSeedAndY(int seed, int y){
		Random yRandomer = new Random(y);
		yRandomer.nextFloat();
		yRandomer.nextFloat();
		return new Random((long) (1f+((float)seed)*yRandomer.nextFloat()));
	}

}
