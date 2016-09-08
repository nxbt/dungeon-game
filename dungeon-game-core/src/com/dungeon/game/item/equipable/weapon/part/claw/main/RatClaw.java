package com.dungeon.game.item.equipable.weapon.part.claw.main;

import com.dungeon.game.world.World;

public class RatClaw extends ClawMain {

	public RatClaw(World world, int level) {
		super(world, "Rat Claw", SPRITES[0], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash"
		};
		bannedSwings = new String[]{};
	}

	@Override
	public void setStats(float level) {
		damage = 4*level;
		speed = 16*level;
		knockback = 3;
		weight = 4;
		numSwings = 3;
	}

}
