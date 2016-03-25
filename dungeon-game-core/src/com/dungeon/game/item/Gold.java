package com.dungeon.game.item;

import com.dungeon.game.world.World;

public class Gold extends Item {

	public Gold(World world) {
		super(world, "coin.png");
		
		type = DEFAULT;
		
		maxStack = Integer.MAX_VALUE;
		
		name = "Gold";
		desc = "Show me da money";
	}

}
