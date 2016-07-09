package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Needle extends SwordBlade {
	public Needle(World world, int level) {
		super(world, "Needle", SPRITES[9], level);
		id = 0;
		allowedSwings = new String[]{
			"Stab",
		};
		bannedSwings = new String[]{
			"Slash",
			"Chop",
			"Whirlwind",
			"Execute"
		};
		repeatable = true;
	}

	@Override
	public void setStats(float level) {
		damage = getStat(3+0.3f*level,0.5f+0.1f*level);
		speed = getStat(22,4);
		knockback = getStat(1.2f,1);
		weight = getStat(1.75f,0.25f);
		numSwings = 2;
	}

}
