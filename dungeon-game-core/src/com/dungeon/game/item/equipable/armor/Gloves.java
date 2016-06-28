package com.dungeon.game.item.equipable.armor;

import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.world.World;

public abstract class Gloves extends Equipable {

	public Gloves(World world, String tex) {
		super(world, tex);
		
		name = "Gloves";
		
		type = GLOVES;
		
		desc = "ERROR: desc = null, programmers = dumb.";

	}

}
