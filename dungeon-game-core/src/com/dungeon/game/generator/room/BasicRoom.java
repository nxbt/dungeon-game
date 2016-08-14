package com.dungeon.game.generator.room;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.furniture.Carpet;
import com.dungeon.game.entity.furniture.Lamp;
import com.dungeon.game.entity.furniture.Torch;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class BasicRoom extends Room {

	public BasicRoom(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		for(int i = 0; i < doorPos.length; i++){
			addToOccupied(doorPos[i][0]+1, doorPos[i][1]);
			addToOccupied(doorPos[i][0]-1, doorPos[i][1]);
			addToOccupied(doorPos[i][0], doorPos[i][1]+1);
			addToOccupied(doorPos[i][0], doorPos[i][1]-1);
		}
		int x, y;
		x = 0;
		for(int i = 0; i < room.length; i++){
			y = i;
			if(!checkOccupied(x, y) && Math.random() < 0.1){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS/4, y*Tile.TS+Tile.TS/2,0));
				addToOccupied(x, y);
			}
		}
		x = room[0].length-1;
		for(int i = 0; i < room.length; i++){
			y = i;
			if(!checkOccupied(x, y) && Math.random() < 0.1){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS*3/4, y*Tile.TS+Tile.TS/2,2));
				addToOccupied(x, y);
			}
		}
		y = 0;
		for(int i = 0; i < room[0].length; i++){
			x = i;
			if(!checkOccupied(x, y) && Math.random() < 0.1){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS/2, y*Tile.TS+Tile.TS/4,3));
				addToOccupied(x, y);
			}
		}
		y = room.length-1;
		for(int i = 0; i < room[0].length; i++){
			x = i;
			if(!checkOccupied(x, y) && Math.random() < 0.1){
				entities.add(new Torch(world, x*Tile.TS+Tile.TS/2, y*Tile.TS+Tile.TS*3/4,1));
				addToOccupied(x, y);
			}
		}
		entities.add(new Lamp(world, room[0].length/2f*Tile.TS,room.length/2f*Tile.TS));
		entities.add(new Carpet(world,room[0].length/2f*Tile.TS,room.length/2f*Tile.TS,room[0].length*2-2,room.length*2-2, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f)));
		
		
		
	}

}
