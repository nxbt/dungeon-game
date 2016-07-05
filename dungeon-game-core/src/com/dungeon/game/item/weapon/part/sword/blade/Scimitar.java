package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.world.World;

public class Scimitar extends SwordBlade {
	
	//we all know its real name is SwashBuckler! <3 
	//due to a type, there will now be a unique item in the game called SwashBucklrrrrrr

	public Scimitar(World world, int level) {
		super(world, "Scimitar", SPRITES[3], level);
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
		damage = 8*level;
		speed = 11*level;
		knockback = 6;
		weight = 9;
	}

}
