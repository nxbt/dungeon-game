package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class SlimBlade extends SwordBlade {

	public SlimBlade(World world, int level) {
		super(world, "Slim Blade", SPRITES[1], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Stab"
		};
		bannedSwings = new String[]{
			
		};
	}

	@Override
	public void setStats(float level) {
		damage = 8*level;
		speed = 12*level;
		knockback = 7;
		weight = 7;
		numSwings = 3;
	}

}
