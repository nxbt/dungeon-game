package com.dungeon.game.item.equipable.weapon.part.axe.blade;

import com.dungeon.game.world.World;

public class SquaredBlade extends AxeBlade {

	public SquaredBlade(World world, int level) {
		super(world, "Square Axe Head", SPRITES[1], level);
		id = 0;
		allowedSwings = new String[]{
			"Chop",
			"SkullSpliter"
		};
		bannedSwings = new String[]{
				
		};
	}

	@Override
	public void setStats(float level) {
		damage = 8*level;
		speed = 11*level;
		knockback = 7;
		weight = 7;
	}

}
