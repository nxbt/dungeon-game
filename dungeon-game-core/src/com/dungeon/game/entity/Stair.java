package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.Floor;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Stair extends Static {
	boolean goDown;
	
	int destX;
	int destY;
	
	public Stair(World world, float x, float y, boolean goDown, int destX, int destY) {
		super(world, x, y);
		
		this.destX = destX;
		this.destY = destY;
		
		this.goDown = goDown;
		
		sprite = new Texture("trapdoor.png");
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		origin_x = 16;
		origin_y = 16;
		
		hitbox = new Polygon(new float[] {0, 0, 32, 0, 32, 32, 0, 32});
	}

	@Override
	public void calc() {
		
	}

	public void hovered() {
		if(world.mouse.lb_pressed && !world.player.fightMode) {
			world.entities.remove(world.player);
			if(world.curDungeon.floors.size() <= world.curDungeon.floors.indexOf(world.curFloor) + (goDown? 1:-1)) {
				//generate new floor
				world.curDungeon.floors.add(new Floor(world, "rooms", 50,50,destX,destY,(int)(x/Tile.TS),(int)(y/Tile.TS)));
			}
			world.curFloor = world.curDungeon.floors.get(world.curDungeon.floors.indexOf(world.curFloor) + (goDown? 1:-1));
			world.entities = world.curFloor.entities;
			world.entities.add(world.player);
			world.areaMap = world.curFloor.areaMap;
			world.player.x = destX*Tile.TS-Tile.TS/2;
			world.player.y = destY*Tile.TS-Tile.TS/2;
		}
	}
	
	@Override
	public void post() {
		
	}

}
