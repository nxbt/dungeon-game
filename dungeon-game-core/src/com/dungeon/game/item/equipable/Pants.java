package com.dungeon.game.item.equipable;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public class Pants extends Equipable {

	public Pants(World world, Color color) {
		super(world, "pants.png");
		
		sprite = Spritesheet.Tint(textures[0], color, true);
		
		name = "Pants";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = PANTS;
		
		desc = "A simple pair of wool pants. Provideds little defence.\n\n Armor: " + (int)physc_resist;

	}

}
