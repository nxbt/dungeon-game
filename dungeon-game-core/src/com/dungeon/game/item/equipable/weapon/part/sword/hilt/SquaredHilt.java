package com.dungeon.game.item.equipable.weapon.part.sword.hilt;

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
		damage = getStat(0.2f+0.03f*level,0.1f+0.01f*level);
		speed = getStat(0.3f,0.1f);
		knockback = getStat(1.3f,0.6f);
		weight = getStat(1.6f,0.3f);
		
	}

}
