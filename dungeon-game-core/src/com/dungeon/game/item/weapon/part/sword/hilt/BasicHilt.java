package com.dungeon.game.item.weapon.part.sword.hilt;

import com.dungeon.game.world.World;

public class BasicHilt extends SwordHilt {

	public BasicHilt(World world) {
		super(world, "Basic Hilt", SPIRTES[0]);
		id = 0;
		dmgMult = 1;
		speedMult = 1;
		knockMult = 1;
		weightMult = 1;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

}
