package com.dungeon.game.item.equipable.armor;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.effect.Armor;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public abstract class Shirt extends Equipable {
	public Shirt(World world, String tex) {
		super(world, tex);
		
		name = "Shirt";
		
		type = CHEST;
		
		desc = "We forgot to write a description for this shirt. Sry bae.";
	}

}
