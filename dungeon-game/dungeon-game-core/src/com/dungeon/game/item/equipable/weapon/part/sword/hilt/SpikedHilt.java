package com.dungeon.game.item.equipable.weapon.part.sword.hilt;

import com.dungeon.game.world.World;

public class SpikedHilt extends SwordHilt {

	public SpikedHilt(World world, int level) {
		super(world, "Spiked Hilt", SPIRTES[2], level);
		id = 0;
		allowedSwings = new String[]{
				"HiltJab"
		};
		bannedSwings = new String[0];
		repeatable = false;
	}

	@Override
	public void setStats(float level) {
		damage = getStat(2.8f+0.2f*level,0.3f*0.04f);
		speed = getStat(0.07f,0.02f);
		knockback = getStat(0.03f,0.01f);
		weight = getStat(1.2f,0.4f);
		
	}

}
