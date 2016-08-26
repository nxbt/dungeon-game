package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Chest extends Container {
	
	public Chest(World world, float x, float y) {
		super(world, x, y, Tile.TS, Tile.TS, "chest.png");
		
		name = "chest";
		
		solid = true;
		
		hitbox = new Polygon(new float[]{0,0,32,0,32,32,0,32});
		genVisBox();
		
		origin_x = 16;
		origin_y = 16;
	}
}
