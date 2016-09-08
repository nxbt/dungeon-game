package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Web extends Static {

	public Web(World world) {
		super(world, 0, 0, 32, 32, "web.png");
		
		originX = 16;
		originY = 16;
		hitbox = new Polygon(new float[]{0,0,32,0,32,32,0,32});
		clickable = false;
		genVisBox();
		
		layer = HANDHELD;
	}

	@Override
	public void calc() {}

	@Override
	public void post() {}

}
