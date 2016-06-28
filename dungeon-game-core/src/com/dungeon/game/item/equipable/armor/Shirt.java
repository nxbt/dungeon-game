package com.dungeon.game.item.equipable.armor;

import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.world.World;

public abstract class Shirt extends Equipable {
	public Shirt(World world, String tex) {
		super(world, tex);
		
		name = "Shirt";
		
		type = CHEST;
		
		desc = "We forgot to write a description for this shirt. Sry bae.";
	}

}
