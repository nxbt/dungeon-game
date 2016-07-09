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
		damage = getStat(10.4f+0.8f*level,0.5f+0.2f*level);
		speed = getStat(9.2f,1.7f);
		knockback = getStat(7.4f,2.6f);
		weight = getStat(13.5f,1.8f);
		numSwings = 2;
	}

}
