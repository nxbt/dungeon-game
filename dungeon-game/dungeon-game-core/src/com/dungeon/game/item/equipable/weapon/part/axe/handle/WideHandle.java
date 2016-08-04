package com.dungeon.game.item.equipable.weapon.part.axe.handle;

import com.dungeon.game.world.World;

public class WideHandle extends AxeHandle {

	public WideHandle(World world, int level) {
		super(world, "Wide Axe Handle", SPRITES[1], level);
		id = 0;
		allowedSwings = new String[]{
		};
		bannedSwings = new String[]{
				
		};
	}

	@Override
	public void setStats(float level) {
		damage = 2*level;
		speed = -1*level;
		knockback = 2;
		weight = 3;
	}

}
