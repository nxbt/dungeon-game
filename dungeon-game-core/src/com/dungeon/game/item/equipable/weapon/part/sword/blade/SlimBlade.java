package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class SlimBlade extends SwordBlade {

	public SlimBlade(World world, int level) {
		super(world, "Slim Blade", SPRITES[1], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Stab"
		};
		bannedSwings = new String[]{
			
		};
		hitbox = new Polygon(new float[]{1,29,23,7,25,9,3,31,1,31});


	}

	@Override
	public void setStats(float level) {
		damage = getStat(7.4f+1.2f*level,1.7f+0.5f*level);
		speed = getStat(12.4f,2.4f);
		knockback = getStat(6.8f,1.7f);
		weight = getStat(7.2f,2.1f);
		numSwings = 3;
	}
	
	public String getName() {
		return "Slim Sword";
	}
}
