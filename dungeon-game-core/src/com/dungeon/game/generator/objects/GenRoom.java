package com.dungeon.game.generator.objects;

import com.dungeon.game.world.World;

public class GenRoom extends GenObject {

	public GenRoom(World world, int x, int y, int width, int height) {
		super(world, x, y, width, height);
		fillRoom();
	}
	
	private void fillRoom(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				reservedTiles[x][y] = true;
			}
		}
	}

}
