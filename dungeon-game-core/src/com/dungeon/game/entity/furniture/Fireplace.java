package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public class Fireplace extends Static {

	public Fireplace(World world, float x, float y) {
		super(world, x, y, 32, 32, "fireplace.png");
		solid = true;
		hitbox = new Polygon(new float[]{0,0,32,0,32,32,0,32});
		light = new Light(world, x, y, 150, 100, Light.ORANGE, 40, this);
		origin_x = 16;
		origin_y = 16;
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
