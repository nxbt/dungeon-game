package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class Triangular extends AxeBlade {

	public Triangular(World world, int level) {
		super(world, "Triangular Axe Head", SPRITES[6], level);
		id = 0;
		allowedSwings = new String[]{
			"Chop",
			"SkullSpliter"
		};
		bannedSwings = new String[]{
			"Cleave"
		};
	}

	@Override
	public void setStats(float level) {
		damage = 14*level;
		speed = 13*level;
		knockback = 3;
		weight = 13;
		numSwings = 2;
	}

}
