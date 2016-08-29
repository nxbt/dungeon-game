package com.dungeon.game.generator.rooms.room;

import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.world.TileMap;
import com.dungeon.game.world.World;

public class EmptyRoom extends Room {

	public EmptyRoom(World world, Rectangle roomBase, int[][] doorFinder, TileMap tileMap) {
		super(world, roomBase, doorFinder, tileMap);
	}

	@Override
	public void generate() {

	}

}
