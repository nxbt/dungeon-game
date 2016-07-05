package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class SlimBlade extends SwordBlade {

	public SlimBlade(World world) {
		super(world, "Slim Blade", SPRITES[1]);
		id = 0;
		dmgMult = 0.7f;
		speedMult = 1.3f;
		knockMult = 0.7f;
		weightMult = 0.7f;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

}
