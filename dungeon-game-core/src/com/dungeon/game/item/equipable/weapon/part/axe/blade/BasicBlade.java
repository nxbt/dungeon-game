package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class BasicBlade extends AxeBlade {

	public BasicBlade(World world, int level) {
		super(world, "Basic Axe Head", SPRITES[0], level);
		id = 0;
		allowedSwings = new String[]{
			"Cleave"
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
