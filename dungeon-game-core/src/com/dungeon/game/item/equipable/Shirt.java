package com.dungeon.game.item.equipable;

import com.dungeon.game.world.World;

public class Shirt extends Equipable {
	public Shirt(World world) {
		super(world, "stick.png");
		
		name = "Shirt";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = CHEST;
		
		desc = "A simple wool shirt. Provideds little defence.\n\n Armor: " + (int)physc_resist;
	}

}
