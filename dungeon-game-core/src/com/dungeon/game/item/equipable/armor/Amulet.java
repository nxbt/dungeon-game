package com.dungeon.game.item.equipable.armor;

import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.world.World;

public class Amulet extends Equipable {

	public Amulet(World world, String tex) {
		super(world, tex);
		
		name = "Amulet";
		
		type = AMULET;
		
		desc = "Amulet you guess what this is, ok?";

	}

}
