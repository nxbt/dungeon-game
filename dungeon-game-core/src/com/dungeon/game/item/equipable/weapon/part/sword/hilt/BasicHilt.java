package com.dungeon.game.item.equipable.weapon.part.sword.hilt;

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
		damage = getStat(0.2f+0.05f*level,0.07f+0.01f*level);
		speed = getStat(1.1f,0.3f);
		knockback = getStat(0.1f,0.02f);
		weight = getStat(1.1f,0.35f);
		
	}

}
