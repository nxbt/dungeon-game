package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class Hatchet extends AxeBlade {

	public Hatchet(World world, int level) {
		super(world, "Hatchet", SPRITES[2], level);
		id = 0;
		allowedSwings = new String[]{
			"Chop"
		};
		bannedSwings = new String[]{
			"Cleave",
			"SkullSpliter"
		};
		repeatable = true;
	}

	@Override
	public void setStats(float level) {
		damage = 6*level;
		speed = 13*level;
		knockback = 7;
		weight = 6;
	}

}
