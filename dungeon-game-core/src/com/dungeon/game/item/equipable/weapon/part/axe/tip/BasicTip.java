package com.dungeon.game.item.equipable.weapon.part.axe.tip;

import com.dungeon.game.world.World;

public class BasicTip extends AxeTip {

	public BasicTip(World world, int level) {
		super(world, "Basic Axe Tip", SPRITES[0], level);
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
		damage = 10*level;
		speed = 10*level;
		knockback = 10;
		weight = 10;
	}

}
