package com.dungeon.game.item.equipable.armor;

import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.world.World;

public abstract class Pants extends Equipable {

	public Pants(World world, String tex) {
		super(world, tex);

		name = "Pants";
		
		type = PANTS;
		
		desc = "Our descriptions have been abducted by aliens. Send help.";

	}

}
