package com.dungeon.game.spell;

import com.dungeon.game.item.Medium;
import com.dungeon.game.world.World;

public abstract class Spell {
	
	protected World world;
	
	public Spell(World world) {
		this.world = world;
	}

	public abstract void cast(float x, float y, float angle, Medium medium);
}
