package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Dao extends SwordBlade {
	public Dao(World world, int level) {
		super(world, "Dao", SPRITES[7], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Whirlwind"
		};
		bannedSwings = new String[]{
			"Stab"
		};
		repeatable = true;
	}

	@Override
	public void setStats(float level) {
		damage = getStat(8.5f+0.9f*level,1.5f+0.5f*level);
		speed = getStat(12.5f,1.5f);
		knockback = getStat(6.5f,2);
		weight = getStat(11,1.5f);
		numSwings = 3;
	}

}
