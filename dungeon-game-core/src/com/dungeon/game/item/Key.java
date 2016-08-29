package com.dungeon.game.item;

import com.dungeon.game.world.World;

public class Key extends Item {

	public Key(World world) {
		super(world, "stick.png");
		
		name = "Key";
		desc = "It opens doors dumbass.";
		
		maxStack = 10;
	}

}
