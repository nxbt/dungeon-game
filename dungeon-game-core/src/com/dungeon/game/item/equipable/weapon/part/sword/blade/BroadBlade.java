package com.dungeon.game.item.equipable.weapon.part.sword.blade;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class BroadBlade extends SwordBlade {
	public BroadBlade(World world, int level) {
		super(world, "Broad Blade", SPRITES[2], level);
		id = 0;
		allowedSwings = new String[]{
			"Slash",
			"Chop"
		};
		bannedSwings = new String[]{
				
		};
		hitbox = new Polygon(new float[]{1,27,21,7,25,11,5,31,1,31});
	}

	@Override
	public void setStats(float level) {
		damage = getStat(13.5f+1.5f*level,2.2f+0.8f*level);
		speed = getStat(7,1.5f);
		knockback = getStat(15,2.5f);
		weight = getStat(13,4);
		numSwings = 3;
	}
	
	public String getName() {
		return "Broad Sword";
	}
}
