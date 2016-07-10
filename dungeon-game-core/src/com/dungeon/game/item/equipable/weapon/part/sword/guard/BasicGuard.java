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
		damage = getStat(0.5f+0.1f*level,0.2f+0.02f*level);
		speed = getStat(0.3f,0.2f);
		knockback = getStat(0.4f,0.2f);;
		weight = getStat(1,0.3f);
		
	}

}
