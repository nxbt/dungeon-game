package com.dungeon.game.item;

import com.dungeon.game.entity.Character;
import com.dungeon.game.world.World;

public abstract class Consumable extends Item {

	public Consumable(String name) {
		super();
		type = 1;
		maxStack = 10;
		this.name = name;
	}

	public abstract void use(World world, Character user);
}
