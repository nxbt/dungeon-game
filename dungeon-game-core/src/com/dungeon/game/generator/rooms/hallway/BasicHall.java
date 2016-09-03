package com.dungeon.game.generator.rooms.hallway;

import java.util.ArrayList;

import com.dungeon.game.entity.furniture.Torch;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class BasicHall extends Hallway {
	
    public BasicHall(World world, ArrayList<int[]> coordinates, TileMap tileMap){
      super(world, coordinates, tileMap);
    }
    
    
	public void generate(){
		int count = (int) Math.floor(Math.random()*2)+2;
		
	    for(int i = 0; i < tiles.length; i++){
	    	count--;
	    	
	    	if(count == 0) {
	    		count = (int) Math.floor(Math.random()*2)+2;
				int dir = cords[i][2];
				//0 = down 1 == up 2 == left 3 == right
				
				if(i < cords.length - 1 && dir != cords[i+1][2]){
					int dirNext = cords[i+1][2];
					if(dir == 0){
						if(dirNext == 2){
							dir = 2;
						}else {
							dir = 0;
						}
					}else if(dir == 1){
						if(dirNext == 2){
							dir = 2;
						}else {
							dir = 0;
						}
					}else if(dir == 2){
						if(dirNext == 0){
							dir = 1;
						}else {
							dir = 3;
						}
					}else if(dir == 3){
						if(dirNext == 0){
							dir = 1;
						}else {
							dir = 3;
						}
					}
				}else{
					
					if(dir == 0 || dir == 1) dir = Math.random()>0.5?0:2;
					else dir = Math.random() >0.5?1:3;
					
				}
				
				//check if we need to change the dir because the next tile is a different dir

				int x = cords[i][0];
				int y = cords[i][1];
				
				entities.add(new Torch(world, x, y, dir));
	    	}
	    }
	}
	
}
