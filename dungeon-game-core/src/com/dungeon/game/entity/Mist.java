package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Mist extends Static {
	
	private com.dungeon.game.textures.entity.Mist texture;

	public Mist(World world, float x, float y, int width, int height) {
		super(world, x, y, 32, 32, "slot.png");
		layer = 2;
		texture = new com.dungeon.game.textures.entity.Mist((int)(Math.random()*1000), (int)x, (int)y, width, height);
		sprite = texture.texture;
		
		d_width = width;
		d_height = width;
		hitbox = new Polygon(new float[]{0,0,0,0,0,0});
		genVisBox();
		clickable = false;
	}

	@Override
	public void calc() {
		texture.update();
		sprite = texture.texture;

	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
