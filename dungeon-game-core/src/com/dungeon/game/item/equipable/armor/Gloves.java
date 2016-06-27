package com.dungeon.game.item.equipable.armor;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public abstract class Gloves extends Equipable {

	public Gloves(World world, Color color) {
		super(world, "pants.png");
		
		sprite = Spritesheet.Tint(textures[0], color, true);
		
		name = "Gloves";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = GLOVES;
		
		desc = "No, really. They're gloves!\n\n Armor: " + (int)physc_resist;

	}

}
