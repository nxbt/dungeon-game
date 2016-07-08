package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Needle extends SwordBlade {

	public Needle(World world, int level) {
		super(world, "Needle", SPRITES[9], level);
		id = 0;
		allowedSwings = new String[]{
			"Stab",
			"Execute",
		};
		bannedSwings = new String[]{
			"Slash",
			"Chop",
			"Whirlwind"
		};
		repeatable = true;
	}

	@Override
	public void setStats(float level) {
		damage = 3*level;
		speed = 26*level;
		knockback = 2;
		weight = 2;
		numSwings = 2;
	}

}
