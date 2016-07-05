package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class SlimBlade extends SwordBlade {

	public SlimBlade(World world, int level) {
		super(world, "Slim Blade", SPRITES[1], level);
		id = 0;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

	@Override
	public void setStats(int level) {
		damage = 8*level;
		speed = 12*level;
		knockback = 7;
		weight = 7;
	}

}
