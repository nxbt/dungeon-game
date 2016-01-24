package com.dungeon.game.item;

import com.dungeon.game.world.World;
import com.dungeon.game.entity.Character;

public abstract class Ammo extends Item {

	public Ammo(World world){
		super(world);
		type = DEFAULT;
		stack = 1;
		maxStack = 12;
	}

}
