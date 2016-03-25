package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public class ShopDesk2 extends Static {

	public ShopDesk2(World world, float x, float y) {
		super(world, x, y);
		
		sprite = new Texture("desk2.png");
		
		d_width = 48;
		d_height = 16;
		
		hitbox = new Polygon(new float[] {0,0,48,0,48,16,0,16});
		
		origin_x = 24;
		origin_y = 8;
		
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
