package com.dungeon.game.generator.rooms.hallway;

import java.util.ArrayList;

import com.dungeon.game.world.TileMap;

public class BasicHall extends Hallway {
    private BasicHall(ArrayList<int[]> coordinates, TileMap tileMap){
      super(coordinates, tileMap);
    }
    
    
	public void generate(){
	    for(int i = 0; i < tiles.length; i++){
	    	tiles[i] = tileMap.getTile(0);
	    }
	}
}
