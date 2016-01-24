package com.dungeon.game.spell;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.FireballGraphic;
import com.dungeon.game.item.Medium;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class Fireball extends Spell {

	public Fireball(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cast(float x, float y, float angle, Medium medium) {
		world.entities.add(new FireballGraphic(world, (int)x, (int)y, angle, 10, new Polygon(new float[]{0,28,4,28,4,32,0,32}), 0, 32, medium));
		
	}

}
