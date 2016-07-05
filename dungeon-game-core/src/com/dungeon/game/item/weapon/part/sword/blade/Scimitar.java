package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Scimitar extends SwordBlade {

	public Scimitar(World world, int level) {
		super(world, "Basic Blade", SPRITES[3], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash"
		};
		bannedSwings = new String[]{
			"Stab"
		};
		repeatable = true;
	}

	@Override
	public void setStats(float level) {
		damage = 10*level;
		speed = 10*level;
		knockback = 10;
		weight = 10;
	}

}
