package com.dungeon.game.generator.rooms.hallway;

import java.util.ArrayList;

import com.dungeon.game.entity.furniture.Torch;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class BasicHall extends Hallway {
	
    public BasicHall(World world, ArrayList<int[]> coordinates, TileMap tileMap){
      super(world, coordinates, tileMap);
    }
    
    
	public void generate(){
		int count = 0;
		
	    for(int i = 0; i < tiles.length; i++){
	    	count += Math.random()>0.5?1:0;
	    	
	    	if(count == 2) {
	    		count = 0;
				int dir = cords[i][2];
				
				if(dir == 0 || dir == 1) dir = Math.random()>0.5?0:2;
				else dir = Math.random() >0.5?1:3;
				
				
				
				entities.add(new Torch(world, cords[i][0]*Tile.TS+Tile.TS/2, cords[i][1]*Tile.TS + Tile.TS/2, dir)); //0 = 1, 1 = 3, 2 = 0, 3 = 2
	    	}
	    }
	}
	
}
