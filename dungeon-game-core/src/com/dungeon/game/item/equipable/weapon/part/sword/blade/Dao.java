package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Dao extends SwordBlade {

	public Dao(World world, int level) {
		super(world, "Dao", SPRITES[7], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Chop"
		};
		bannedSwings = new String[]{
			"Stab",
		};
		repeatable = true;
	}

	@Override
	public void setStats(float level) {
		damage = 12*level;
		speed = 10*level;
		knockback = 8;
		weight = 11;
	}

}
