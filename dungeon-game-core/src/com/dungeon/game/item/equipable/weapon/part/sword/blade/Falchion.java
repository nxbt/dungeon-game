package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Falchion extends SwordBlade {

	public Falchion(World world, int level) {
		super(world, "Falchion", SPRITES[5], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Chop"
		};
		bannedSwings = new String[]{
			"Stab"
		};
	}

	@Override
	public void setStats(float level) {
		damage = 15*level;
		speed = 9*level;
		knockback = 13;
		weight = 13;
		numSwings = 2;
	}

}
