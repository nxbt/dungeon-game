package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class BasicBlade extends SwordBlade {
	public BasicBlade(World world, int level) {
		super(world, "Basic Blade", SPRITES[0], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Stab"
		};
		bannedSwings = new String[]{
				
		};
		hitbox = new Polygon(new float[]{1,28,22,7,25,10,4,31,1,31});
	}

	@Override
	public void setStats(float level) {
		damage = getStat(9+level,1.6f+level*0.4f);
		speed = getStat(10,2);
		knockback = getStat(10,2);
		weight = getStat(10,2);
		numSwings = 3;
	}
}
