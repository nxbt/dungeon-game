package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class BasicBlade extends SwordBlade {

	public BasicBlade(World world, int level) {
		super(world, "Basic Blade", SPRITES[0], level);
		id = 0;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

	@Override
	public void setStats(int level) {
		damage = 10*level;
		speed = 10*level;
		knockback = 10;
		weight = 10;
	}

}
