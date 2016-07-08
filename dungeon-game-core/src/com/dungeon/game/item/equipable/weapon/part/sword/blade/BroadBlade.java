package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class BroadBlade extends SwordBlade {

	public BroadBlade(World world, int level) {
		super(world, "Broad Blade", SPRITES[2], level);
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
		damage = 12*level;
		speed = 7*level;
		knockback = 12;
		weight = 13;
		numSwings = 3;
	}

}
