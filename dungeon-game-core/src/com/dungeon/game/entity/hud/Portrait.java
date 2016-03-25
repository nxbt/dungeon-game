package com.dungeon.game.entity.hud;

import com.dungeon.game.world.World;

public class Portrait extends Hud {

	public Portrait(World world, float x, float y) {
		super(world, x, y, 68, 68, "face.png");
	}

	@Override
	public void calc() {}

	@Override
	public void post() {}

}
