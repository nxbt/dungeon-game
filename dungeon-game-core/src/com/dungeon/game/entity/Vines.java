package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Vines extends Static {

	public Vines(World world) {
		super(world, 0, 0, 32, 32, "tangled.png");
		
		origin_x = 16;
		origin_y = 16;
		hitbox = new Polygon(new float[]{0,0,0,0,0,0});
		genVisBox();
	}

	@Override
	public void calc() {}

	@Override
	public void post() {}

}
