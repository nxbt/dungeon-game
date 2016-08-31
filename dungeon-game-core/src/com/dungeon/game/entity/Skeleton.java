package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Skeleton extends Static {

	public Skeleton(World world, float x, float y) {
		super(world, x, y, 32, 32, "slot.png");
		
		sprite = new com.dungeon.game.textures.entity.Skeleton().texture;
		originX = 16;
		originY = 16;
		hitbox = new Polygon(new float[]{0, 0, 32, 0, 32, 32, 0, 32});
		genVisBox();
		layer = FLOOR;
		solid = false;
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
