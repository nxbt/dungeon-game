package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class BasicBlade extends SwordBlade {

	public BasicBlade(World world) {
		super(world, "Basic Blade", SPRITES[0]);
		id = 0;
		dmgMult = 1;
		speedMult = 1;
		knockMult = 1;
		weightMult = 1;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

}
