package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class Cleaver extends AxeBlade {

	public Cleaver(World world, int level) {
		super(world, "Cleaver", SPRITES[4], level);
		id = 0;
		allowedSwings = new String[]{
			"Cleave",
			"SkullSpliter"
		};
		bannedSwings = new String[]{
				"Chop",
		};
	}

	@Override
	public void setStats(float level) {
		damage = 9*level;
		speed = 13*level;
		knockback = 6;
		weight = 11;
		numSwings = 2;
	}

}
