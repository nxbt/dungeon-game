package com.dungeon.game.generator.room;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.entity.furniture.Carpet;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class Alter extends Room {
	int[][] doorPos;

	public Alter(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		doorPos = new int[doorFinder.length][2];
		for(int i = 0; i < doorFinder.length; i++){
			doorPos[i][0] = doorFinder[i][1];
			doorPos[i][1] = doorFinder[i][2];
		}		
		entities.add(new Carpet(world,room[0].length/2f*Tile.TS,room.length/2f*Tile.TS,room[0].length*2-2,room.length*2-2, new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),0.5f)));

		
		
	}

}
