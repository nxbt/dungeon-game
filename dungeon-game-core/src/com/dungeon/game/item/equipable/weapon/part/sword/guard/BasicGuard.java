package com.dungeon.game.item.equipable.weapon.part.sword.guard;

import com.dungeon.game.world.World;

public class BasicGuard extends SwordGuard {

	public BasicGuard(World world, int level) {
		super(world, "Basic Guard", SPIRTES[0], level);
		id = 0;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

	@Override
	public void setStats(float level) {
		damage = 0;
		speed = -1*level;
		knockback = 1;
		weight = 1;
		
	}

}
