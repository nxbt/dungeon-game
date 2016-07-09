package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class Tomahawk extends AxeBlade {

	public Tomahawk(World world, int level) {
		super(world, "Tomahawk", SPRITES[7], level);
		id = 0;
		allowedSwings = new String[]{
			"Chop",
			"SkullSpliter"
			//it the future a special swing that uses the spike!
		};
		bannedSwings = new String[]{
			"Cleave"
		};
	}

	@Override
	public void setStats(float level) {
		damage = 7*level;
		speed = 12*level;
		knockback = 7;
		weight = 7;
		numSwings = 4;
	}

}
