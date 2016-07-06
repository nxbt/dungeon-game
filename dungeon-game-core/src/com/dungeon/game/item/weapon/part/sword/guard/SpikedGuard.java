package com.dungeon.game.item.weapon.part.sword.guard;

import com.dungeon.game.world.World;

public class SpikedGuard extends SwordGuard {

	public SpikedGuard(World world, int level) {
		super(world, "Spiked Hilt", SPIRTES[2], level);
		id = 0;
		allowedSwings = new String[]{
				"GuardJab"
		};
		bannedSwings = new String[0];
		repeatable = false;
	}

	@Override
	public void setStats(float level) {
		damage = 3*level;
		speed = -1*level;
		knockback = -1;
		weight = 1;
		
	}

}
