package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class AngularBlade extends AxeBlade {

	public AngularBlade(World world, int level) {
		super(world, "Angular Blade", SPRITES[3], level);
		id = 0;
		allowedSwings = new String[]{
			"Chop",
			"SkullSpliter"
		};
		bannedSwings = new String[]{
			"Cleave",
		};
	}

	@Override
	public void setStats(float level) {
		damage = 8*level;
		speed = 9*level;
		knockback = 10;
		weight = 11;
		numSwings = 2;
	}

}
