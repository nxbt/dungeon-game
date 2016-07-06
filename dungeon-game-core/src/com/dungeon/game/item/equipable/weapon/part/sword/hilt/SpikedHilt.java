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
		damage = 3 * level;
		speed = -1*level;
		knockback = -1;
		weight = 1;
		
	}

}
