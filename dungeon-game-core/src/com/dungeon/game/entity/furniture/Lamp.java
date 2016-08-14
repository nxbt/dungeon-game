package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public class Lamp extends Static {

	public Lamp(World world, float x, float y) {
		super(world, x, y, 32, 32, "slot.png");
		sprite = new com.dungeon.game.textures.entity.Lamp().texture;

		solid = true;
		
		origin_x = 16;
		origin_y = 16;
		
		hitbox = new Polygon(new float[]{8,8,24,8,24,24,8,24});
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
