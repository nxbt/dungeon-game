package com.dungeon.game.item.equipable.armor;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public abstract class Ring extends Equipable {

	public Ring(World world, String tex) {
		super(world, tex);
		
		name = "Ring";
		
		type = RING;
		
		desc = "We are lazy and incapable of writing descriptions.";

	}

}
