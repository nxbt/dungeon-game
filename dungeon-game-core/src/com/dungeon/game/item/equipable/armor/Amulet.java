package com.dungeon.game.item.equipable.armor;

import com.badlogic.gdx.graphics.Color;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public class Amulet extends Equipable {

	public Amulet(World world, Color color) {
		super(world, "pants.png");
		
		sprite = Spritesheet.Tint(textures[0], color, true);
		
		name = "Amulet";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = AMULET;
		
		desc = "No, really. It's an amulet!\n\n Armor: " + (int)physc_resist;

	}

}
