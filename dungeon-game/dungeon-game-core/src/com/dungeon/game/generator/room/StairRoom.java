package com.dungeon.game.generator.room;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.friend.StairKeeper;
import com.dungeon.game.entity.furniture.Stair;
import com.dungeon.game.entity.furniture.Torch;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class StairRoom extends Room {

	public StairRoom(World world, Rectangle roomBase, int[] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		int doorX =  doorPos[0];
		int doorY = doorPos[1];
		
		//spawn entities and change tiles below
		
		//change floor to tiles
		for(int i = 0; i < room.length; i++){
			for(int k = 0; k < room[i].length; k++){
				room[i][k] = tileMap.getTile(3);
			}
		}
				
		int x, y;
		if(room.length%2 == 0)y = room.length/2 - (int) (Math.random()*2);
		else y = room.length/2;
		
		if(room[0].length%2 == 0)x = room[0].length/2 - (int) (Math.random()*2);
		else x = room[0].length/2;
		
		//spawn stairs
		entities.add(new Stair(world,x*Tile.TS+Tile.TS/2 , y*Tile.TS+Tile.TS/2, true, 15+(int) (Math.random()*10), 15+((int) Math.random()*10)));
		
		//add pilars
		room[1][1] =  tileMap.getTile(1);
		room[1][room[0].length-2] =  tileMap.getTile(1);
		room[room.length-2][1] =  tileMap.getTile(1);
		room[room.length-2][room[0].length-2] =  tileMap.getTile(1);
		
		//add torches
		entities.add(new Torch(world, 2*Tile.TS+Tile.TS/4, 1*Tile.TS+Tile.TS/2,0));
		entities.add(new Torch(world, 1*Tile.TS+Tile.TS/2, 2*Tile.TS+Tile.TS/4,3));
		
		entities.add(new Torch(world, (room[0].length-2)*Tile.TS+Tile.TS/2, 2*Tile.TS+Tile.TS/4,3));
		entities.add(new Torch(world, (room[0].length-3)*Tile.TS+Tile.TS*3/4, 1*Tile.TS+Tile.TS/2,2));
		
		entities.add(new Torch(world, 2*Tile.TS+Tile.TS/4, (room.length-2)*Tile.TS+Tile.TS/2,0));
		entities.add(new Torch(world, 1*Tile.TS+Tile.TS/2, (room.length-3)*Tile.TS+Tile.TS*3/4,1));
		
		entities.add(new Torch(world, (room[0].length-3)*Tile.TS+Tile.TS*3/4, (room.length-2)*Tile.TS+Tile.TS/2,2));
		entities.add(new Torch(world, (room[0].length-2)*Tile.TS+Tile.TS/2, (room.length-3)*Tile.TS+Tile.TS*3/4,1));
		
		//spawn hatchkeeper
		boolean keeperTop = doorY >= room.length/2;
		if(doorY == room.length/2 && room.length%2 == 1)keeperTop = Math.random()> 0.5;
		
		entities.add(new StairKeeper(world, 2*Tile.TS+Tile.TS/2,(keeperTop?room.length - 2:1)*Tile.TS+Tile.TS/2));

	}

}
