package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Seax extends SwordBlade {

	public Seax(World world, int level) {
		super(world, "Seax", SPRITES[8], level);
		id = 0;
		allowedSwings = new String[]{
			"Chop",
		};
		bannedSwings = new String[]{
			"Stab",
		};
	}

	@Override
	public void setStats(float level) {
		damage = 9*level;
		speed = 12*level;
		knockback = 8;
		weight = 13;
		numSwings = 2;
	}

}
