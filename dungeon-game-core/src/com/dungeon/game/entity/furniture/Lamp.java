package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public class Lamp extends Static {

	public Lamp(World world, float x, float y) {
		super(world, x, y, 32, 32, "slot.png");
		sprite = new com.dungeon.game.textures.entity.Lamp().texture;

		solid = true;
		
		originX = 16;
		originY = 16;
		
		layer = 3;

		light = new Light(world, x, y, 200, 100, Light.WHITE, 0, this);
		
		hitbox = new Polygon(new float[]{12,12,20,12,20,20,12,20});
		genVisBox();
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
