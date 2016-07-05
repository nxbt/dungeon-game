package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class BroadBlade extends SwordBlade {

	public BroadBlade(World world, int level) {
		super(world, "Broad Blade", SPRITES[2], level);
		id = 0;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

	@Override
	public void setStats(float level) {
		damage = 12*level;
		speed = 7*level;
		knockback = 12;
		weight = 13;
	}

}
