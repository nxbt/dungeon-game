package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Rapier extends SwordBlade {
	
	//we all know its real name is SwashBuckler! <3 
	//due to a type, there will now be a unique item in the game called SwashBucklrrrrrr

	public Rapier(World world, int level) {
		super(world, "Rapier", SPRITES[4], level);
		id = 0;
		allowedSwings = new String[]{
			"Stab"
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
		damage = 6*level;
		speed = 18*level;
		knockback = 3;
		weight = 4;
	}

}
