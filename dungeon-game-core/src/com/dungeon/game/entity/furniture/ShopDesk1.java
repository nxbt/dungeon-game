package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public class ShopDesk1 extends Static {

	public ShopDesk1(World world, float x, float y) {
		super(world, x, y, 16, 48, "desk1.png");
		
		hitbox = new Polygon(new float[] {0,0,16,0,16,48,0,48});
		genVisBox();
		
		originX = 8;
		originY = 24;
		
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
