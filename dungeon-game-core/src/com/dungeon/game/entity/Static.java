package com.dungeon.game.entity;

import com.dungeon.game.world.World;

public abstract class Static extends Entity {
	public Static(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
	}
}