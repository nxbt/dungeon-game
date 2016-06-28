package com.dungeon.game.item.equipable.armor;

import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.world.World;

public abstract class Boots extends Equipable {

	public Boots(World world, String tex) {
		super(world, tex);
		
		name = "Boots";
		
		type = BOOTS;
		
		desc = "The description for this failed to boot.";

	}

}
