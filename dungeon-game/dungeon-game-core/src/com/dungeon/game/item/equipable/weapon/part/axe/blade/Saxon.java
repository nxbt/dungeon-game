package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class Saxon extends AxeBlade {

	public Saxon(World world, int level) {
		super(world, "Saxon Axe Head", SPRITES[5], level);
		id = 0;
		allowedSwings = new String[]{
			"Chop",
			"Cleave"
		};
		bannedSwings = new String[]{
			"SkullSpliter"
		};
	}

	@Override
	public void setStats(float level) {
		damage = 10*level;
		speed = 11*level;
		knockback = 7;
		weight = 9;
		numSwings = 3;
	}

}
