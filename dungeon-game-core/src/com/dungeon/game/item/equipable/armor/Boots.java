package com.dungeon.game.item.equipable.armor;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public class Boots extends Equipable {

	public Boots(World world, Color color) {
		super(world, "pants.png");
		
		sprite = Spritesheet.Tint(textures[0], color, true);
		
		name = "Boots";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = BOOTS;
		
		desc = "No, really. It's boots!\n\n Armor: " + (int)physc_resist;

	}

}
