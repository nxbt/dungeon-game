package com.dungeon.game.textures;

public class dirt extends proceduralTile {

	public dirt(int seed, int x, int y) {
		super(seed, x, y, new int[]{seed, x, y});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture(int args[]) {
		seed = args[0];
		x = args[1];
		y = args[2];
		
		int curX;
		int curY;
		for(int i = 0; i < 32; i++){
			for(int k = 0; k < 32; k++){
				curX = x+i;
				curY = y+k;
			}
		}

	}

}
