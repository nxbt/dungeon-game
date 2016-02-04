package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Vines extends Static {

	public Vines(World world) {
		super(world, 0, 0);
		this.sprite = new Texture("tangled.png");
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		origin_x = 16;
		origin_y = 16;
		hitbox = new Polygon(new float[]{0,0,0,0,0,0});
	}

	@Override
	public void calc() {}

	@Override
	public void post() {}

}
