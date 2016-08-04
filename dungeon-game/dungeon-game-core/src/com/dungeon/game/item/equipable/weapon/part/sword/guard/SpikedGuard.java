package com.dungeon.game.item.equipable.weapon.part.sword.guard;

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
		damage = getStat(2.5f+0.3f*level,0.4f+0.07f*level);
		speed = getStat(0.2f,0.07f);
		knockback = getStat(0.05f,0.02f);
		weight = getStat(1.1f,0.35f);
		
	}

}
