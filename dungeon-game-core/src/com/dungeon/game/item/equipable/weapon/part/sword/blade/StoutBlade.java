package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class StoutBlade extends SwordBlade {

	public StoutBlade(World world, int level) {
		super(world, "Stout Blade", SPRITES[6], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Stab",
			"Chop"
		};
		bannedSwings = new String[]{
			
		};
	}

	@Override
	public void setStats(float level) {
		damage = 15*level;
		speed = 6*level;
		knockback = 13;
		weight = 16;
		numSwings = 3;
	}

}
