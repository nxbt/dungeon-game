package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public class SmallTable extends Static {

	public SmallTable(World world, float x, float y) {
		super(world, x, y, 16, 16, "bedside.png");
		solid = true;
		hitbox = new Polygon(new float[]{1,1,15,1,15,15,1,15});
		origin_x = 8;
		origin_y = 8;
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
