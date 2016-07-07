package com.dungeon.game.item.equipable.weapon.part.axe.handle;

import com.dungeon.game.world.World;

public class BasicHandle extends AxeHandle {

	public BasicHandle(World world, int level) {
		super(world, "Basic Axe Handle", SPRITES[0], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Stab"
		};
		bannedSwings = new String[]{
				
		};
	}

	@Override
	public void setStats(float level) {
		damage = 10*level;
		speed = 10*level;
		knockback = 10;
		weight = 10;
	}

}
