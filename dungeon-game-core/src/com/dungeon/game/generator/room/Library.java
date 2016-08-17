package com.dungeon.game.generator.room;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.furniture.Bookshelf;
import com.dungeon.game.entity.furniture.Lamp;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class Library extends Room {

	public Library(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		
	}

	@Override
	public void generate() {
		System.out.println(doorPos.length);
		for(int i = 0; i < doorPos.length; i++){
			addToOccupied(doorPos[i][0]+1, doorPos[i][1]);
			addToOccupied(doorPos[i][0]-1, doorPos[i][1]);
			addToOccupied(doorPos[i][0], doorPos[i][1]+1);
			addToOccupied(doorPos[i][0], doorPos[i][1]-1);
		}
		
		if(checkOccupied(0, 0)){
			addToOccupied(1,0);
			addToOccupied(0,1);
		}
		if(checkOccupied(room[0].length - 1, 0)){
			addToOccupied(room[0].length - 2, 0);
			addToOccupied(room[0].length - 1, 1);
		}
		if(checkOccupied(0, room.length - 1)){
			addToOccupied(1, room.length - 1);
			addToOccupied(0, room.length - 2);
		}
		if(checkOccupied(room[0].length - 1, room.length - 1)){
			addToOccupied(room[0].length - 2, room.length - 1);
			addToOccupied(room[0].length - 1, room.length - 2);
		}
		
		int x, y;
		int length;
		int[][][] shelfMap;
		x = 0;
		y = 0;
		length = 0;
		for(int i = 0; i < room[0].length; i++){
			if(checkOccupied(i, y)){
				if(length > 0){
					shelfMap = new int[1][length*2][2];
					for(int k = 0; k < shelfMap[0].length; k++){
						shelfMap[0][k][0] = 0;
						shelfMap[0][k][1] = 0;
					}
					if(x == 0)shelfMap[0][0][0] = 1;
					entities.add(new Bookshelf(world, x*Tile.TS+(length*Tile.TS/2), y*Tile.TS+Tile.TS/4f, shelfMap));
				}
				length = 0;
				x = i+1;
			}else{
				length++;
				if(i == room[0].length - 1){
					shelfMap = new int[1][length*2][2];
					for(int k = 0; k < shelfMap[0].length; k++){
						shelfMap[0][k][0] = 0;
						shelfMap[0][k][1] = 0;
					}
					if(x == 0)shelfMap[0][0][0] = 1;
					shelfMap[0][shelfMap[0].length-1][0] = 1;
					entities.add(new Bookshelf(world, x*Tile.TS+(length*Tile.TS/2), y*Tile.TS+Tile.TS/4f, shelfMap));
					length = 0;
				}
			}
		}
		
		x = 0;
		y = room.length - 1;
		length = 0;
		for(int i = 0; i < room[0].length; i++){
			if(checkOccupied(i, y)){
				if(length > 0){
					shelfMap = new int[1][length*2][2];
					for(int k = 0; k < shelfMap[0].length; k++){
						shelfMap[0][k][0] = 0;
						shelfMap[0][k][1] = 2;
					}
					if(x == 0)shelfMap[0][0][0] = 1;
					entities.add(new Bookshelf(world, x*Tile.TS+(length*Tile.TS/2), y*Tile.TS+Tile.TS*3f/4f, shelfMap));
				}
				length = 0;
				x = i+1;
			}else{
				length++;
				if(i == room[0].length - 1){
					shelfMap = new int[1][length*2][2];
					for(int k = 0; k < shelfMap[0].length; k++){
						shelfMap[0][k][0] = 0;
						shelfMap[0][k][1] = 2;
					}
					if(x == 0)shelfMap[0][0][0] = 1;
					shelfMap[0][shelfMap[0].length-1][0] = 1;
					entities.add(new Bookshelf(world, x*Tile.TS+(length*Tile.TS/2), y*Tile.TS+Tile.TS*3f/4f, shelfMap));
					length = 0;
				}
			}
		}
		
		x = 0;
		y = 0;
		length = 0;
		for(int i = 0; i < room.length; i++){
			if(checkOccupied(x, i)){
				if(length > 0){
					if(y == 0)shelfMap = new int[length*2-1][1][2];
					else shelfMap = new int[length*2][1][2];
					for(int k = 0; k < shelfMap.length; k++){
						shelfMap[k][0][0] = 0;
						shelfMap[k][0][1] = 1;
					}
					entities.add(new Bookshelf(world, x*Tile.TS+Tile.TS/4f, y*Tile.TS+(length*Tile.TS/2)+(y == 0?Tile.TS/4:0), shelfMap));
				}
				length = 0;
				y = i+1;
			}else{
				length++;
				if(i == room.length - 1){
					if(y == 0)shelfMap = new int[length*2 - 2][1][2];
					else shelfMap = new int[length*2 - 1][1][2];
					for(int k = 0; k < shelfMap.length; k++){
						shelfMap[k][0][0] = 0;
						shelfMap[k][0][1] = 1;
					}
					entities.add(new Bookshelf(world, x*Tile.TS+Tile.TS/4f, y*Tile.TS+(length*Tile.TS/2)-(y == 0?0:Tile.TS/4), shelfMap));
					length = 0;
				}
			}
		}
		

		
		x = room[0].length-1;
		y = 0;
		length = 0;
		for(int i = 0; i < room.length; i++){
			if(checkOccupied(x, i)){
				if(length > 0){
					if(y == 0)shelfMap = new int[length*2-1][1][2];
					else shelfMap = new int[length*2][1][2];
					for(int k = 0; k < shelfMap.length; k++){
						shelfMap[k][0][0] = 0;
						shelfMap[k][0][1] = 3;
					}
					entities.add(new Bookshelf(world, x*Tile.TS+Tile.TS*3f/4f, y*Tile.TS+(length*Tile.TS/2)+(y == 0?Tile.TS/4:0), shelfMap));
				}
				length = 0;
				y = i+1;
			}else{
				length++;
				if(i == room.length - 1){
					if(y == 0)shelfMap = new int[length*2 - 2][1][2];
					else shelfMap = new int[length*2 - 1][1][2];
					for(int k = 0; k < shelfMap.length; k++){
						shelfMap[k][0][0] = 0;
						shelfMap[k][0][1] = 3;
					}
					entities.add(new Bookshelf(world, x*Tile.TS+Tile.TS*3f/4f, y*Tile.TS+(length*Tile.TS/2)-(y == 0?0:Tile.TS/4), shelfMap));
					length = 0;
				}
			}
		}
		
		entities.add(new Lamp(world, room[0].length*Tile.TS/2, room.length*Tile.TS/2));

	}

}
