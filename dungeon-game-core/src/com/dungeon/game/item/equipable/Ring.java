package com.dungeon.game.item.equipable;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public class Ring extends Equipable {

	public Ring(World world, Color color) {
		super(world, "pants.png");
		
		sprite = Spritesheet.Tint(textures[0], color, true);
		
		name = "Ring";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = RING;
		
		desc = "No, really. It's a ring!\n\n Armor: " + (int)physc_resist;

	}

}
