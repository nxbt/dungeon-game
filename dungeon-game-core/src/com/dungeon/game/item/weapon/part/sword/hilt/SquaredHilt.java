package com.dungeon.game.item.weapon.part.sword.hilt;

import com.dungeon.game.world.World;

public class SquaredHilt extends SwordHilt {

	public SquaredHilt(World world, int level) {
		super(world, "Squared Hilt", SPIRTES[1], level);
		id = 0;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

	@Override
	public void setStats(float level) {
		damage = 0;
		speed = 0.3f*level;
		knockback = 1.5f;
		weight = 1.5f;
		
	}

}
