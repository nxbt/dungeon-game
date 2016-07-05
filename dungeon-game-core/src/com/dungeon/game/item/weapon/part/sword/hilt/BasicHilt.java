package com.dungeon.game.item.weapon.part.sword.hilt;

import com.dungeon.game.world.World;

public class BasicHilt extends SwordHilt {

	public BasicHilt(World world, int level) {
		super(world, "Basic Hilt", SPIRTES[0], level);
		id = 0;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

	@Override
	public void setStats(float level) {
		damage = 0;
		speed = 1;
		knockback = 0;
		weight = 1;
		
	}

}
