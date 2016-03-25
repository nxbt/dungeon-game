package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public class ShopDesk1 extends Static {

	public ShopDesk1(World world, float x, float y) {
		super(world, x, y);
		
		sprite = new Texture("desk1.png");
		
		d_width = 16;
		d_height = 48;
		
		hitbox = new Polygon(new float[] {0,0,16,0,16,48,0,48});
		
		origin_x = 8;
		origin_y = 24;
		
		rotate = true;
		
		solid = true;
	}

	@Override
	public void calc() {
		
	}

	@Override
	public void post() {
		
	}

}
