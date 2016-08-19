package com.dungeon.game.generator.rooms.room;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.friend.Trainer;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class TrainingCenter extends Room {

	public TrainingCenter(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		int doorX =  doorPos[0][0];
		int doorY = doorPos[0][1];;
		
		//spawn stuff
		int x = 0,y = 0;
		
		//change floor to tiles
		for(int i = 0; i < room.length; i++){
			for(int k = 0; k < room[i].length; k++){
				room[i][k] = tileMap.getTile(3);
			}
		}
		
		//generate things!
		entities.add(new Trainer(world, Tile.TS, Tile.TS));

	}

}
