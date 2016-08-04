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
		damage = getStat(0.2f+0.02f*level,0.05f+0.01f*level);
		speed = getStat(0.1f,0.04f);
		knockback = getStat(1,0.3f);
		weight = getStat(1.6f,0.3f);
		
	}

}
