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
		damage = getStat(12.2f+0.8f*level,1.4f+0.6f*level);
		speed = getStat(10.2f,0.8f);
		knockback = getStat(12,1.6f);
		weight = getStat(15,2.5f);
		numSwings = 2;
	}

}
