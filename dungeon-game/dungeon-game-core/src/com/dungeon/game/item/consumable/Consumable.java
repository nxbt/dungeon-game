package com.dungeon.game.item.consumable;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public abstract class Consumable extends Item {

	public Consumable(World world, String name, String filename) {
		super(world, filename);
		type = CONSUM;
		maxStack = 10;
		this.name = name;
	}

	public abstract boolean use(Character user);
}
