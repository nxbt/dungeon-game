package com.dungeon.game.item.equipable.weapon.part.sword.guard;

import com.dungeon.game.world.World;

public class DefendersGuard extends SwordGuard {

	public DefendersGuard(World world, int level) {
		super(world, "Defender's Guard", SPIRTES[1], level);
		id = 0;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

	@Override
	public void setStats(float level) {
		damage = 0;
		speed = 0;
		knockback = 2;
		weight = 2;
		
	}

}
