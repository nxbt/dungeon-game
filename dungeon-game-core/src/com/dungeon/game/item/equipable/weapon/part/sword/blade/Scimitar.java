package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Scimitar extends SwordBlade {
	public Scimitar(World world, int level) {
		super(world, "Scimitar", SPRITES[3], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Chop"
		};
		bannedSwings = new String[]{
			"Stab"
		};
		repeatable = true;
		hitbox = new Polygon(new float[]{3,26,5,19,12,12,22,7,25,10,17,14,10,21,7,23,5,30});
	}

	@Override
	public void setStats(float level) {
		damage = getStat(10.2f+0.8f*level,1.8f+0.4f*level);
		speed = getStat(10.2f,1.5f);
		knockback = getStat(5.7f,1.3f);
		weight = getStat(11.3f,1.7f);
		numSwings = 2;
	}

}
