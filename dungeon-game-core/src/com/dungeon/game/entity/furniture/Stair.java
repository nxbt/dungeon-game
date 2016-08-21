package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Stair extends Static {
	boolean goDown;
	
	int destX;
	int destY;
	
	public Stair(World world, float x, float y, boolean goDown, int destX, int destY) {
		super(world, x, y, Tile.TS, Tile.TS, "trapdoor.png");
		
		layer = 0;
		
		this.destX = destX;
		this.destY = destY;
		
		this.goDown = goDown;
		
		origin_x = 16;
		origin_y = 16;
		
		hitbox = new Polygon(new float[] {0, 0, 32, 0, 32, 32, 0, 32});
		genVisBox();
	}

	@Override
	public void calc() {
		
	}

	public void hovered() {
		if(world.mouse.lb_pressed && !world.player.fightMode) {
			world.changeFloor(world.curDungeon.floors.indexOf(world.curFloor) + (goDown? 1:-1), destX, destY, x, y);
		}
	}
	
	@Override
	public void post() {
		
	}

}
