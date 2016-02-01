package com.dungeon.game.entity;

import com.dungeon.game.effect.Immune;
import com.dungeon.game.world.World;

public abstract class Friend extends Character {
	
	protected int speechRadius;

	public Friend(World world, float x, float y) {
		super(world, x, y);
		addEffect(new Immune(world, -1));
	}
}
