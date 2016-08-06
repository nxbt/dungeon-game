package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Falchion extends SwordBlade {
	public Falchion(World world, int level) {
		super(world, "Falchion Blade", SPRITES[5], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Chop"
		};
		bannedSwings = new String[]{
			"Stab"
		};
		hitbox = new Polygon(new float[]{3,21,22,6,25,8,3,31});
	}

	@Override
	public void setStats(float level) {
		damage = getStat(12.2f+0.8f*level,1.4f+0.6f*level);
		speed = getStat(10.2f,0.8f);
		knockback = getStat(12,1.6f);
		weight = getStat(15,2.5f);
		numSwings = 2;
	}
	
	public String getName() {
		return "Falchion";
	}
}
