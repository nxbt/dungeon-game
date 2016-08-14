package com.dungeon.game.generator.room;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.furniture.Bookshelf;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class Library extends Room {

	public Library(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		
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
		int length;
		x = 0;
		y = 0;
		length = 0;
		for(int i = 0; i < room[0].length; i++){
			if(checkOccupied(i, y)){
				if(length > 0){
					entities.add(new Bookshelf(world, x+length*Tile.TS/2, y+Tile.TS/4, length*2, 1));
				}
				length = 0;
				x = i;
			}else{
				length++;
				if(i == room[0].length - 1){
					entities.add(new Bookshelf(world, x+length*Tile.TS/2, y+Tile.TS/4, length*2, 1));
					length = 0;
				}
			}
		}

	}

}
