package com.dungeon.game.entity.hud;

import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public abstract class Hud extends Static {

	public Hud(World world, float x, float y) {
		super(world, x, y);
	}
}