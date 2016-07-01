package com.dungeon.game.item.equipable.armor;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.effect.Armor;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.World;

public class WoolPants extends Pants {

	public WoolPants(World world, Color color) {
		super(world, "pants.png");
		
		sprite = Spritesheet.Tint(textures[0], color, true);
		
		name = "Wool Pants";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = PANTS;
		
		desc = "A simple pair of wool pants. Provideds little defence.\n\n Armor: " + (int)physc_resist;
		
		passiveEffects.add(new Armor(world, (int) physc_resist));
	}

}
