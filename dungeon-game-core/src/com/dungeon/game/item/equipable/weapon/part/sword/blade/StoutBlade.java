package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class StoutBlade extends SwordBlade {

	public StoutBlade(World world, int level) {
		super(world, "Stout Blade", SPRITES[6], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Stab",
			"Chop"
		};
		bannedSwings = new String[]{
			
		};
		hitbox = new Polygon(new float[]{1,29,18,8,24,8,24,14,3,31,1,31});

	}

	@Override
	public void setStats(float level) {
		damage = getStat(17+2*level,3.5f+1.5f*level);
		speed = getStat(6,1.5f);
		knockback = getStat(17,3);
		weight = getStat(17.5f,2.5f);
		numSwings = 3;
	}
	
	public String getName() {
		return "Stout Sword";
	}
}
