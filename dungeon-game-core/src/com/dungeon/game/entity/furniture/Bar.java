package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Bar extends Static {

	public Bar(World world, float x, float y, int rotation) {
		super(world, x*Tile.TS + Tile.TS/2, y*Tile.TS + Tile.TS/2, 32, 32, "slot.png");
		
		sprite = new com.dungeon.game.textures.entity.Bar(rotation).texture;

		originX = 16;
		originY = 16;
		hitbox = new Polygon(new float[]{0, 0, 32, 0, 32, 32, 0, 32});
		genVisBox();
		layer = 3;
		solid = true;
		if(rotation == 0 || rotation == 2){
			dWidth = 32;
			dHeight = 8;
			hitbox = new Polygon(new float[]{0,0,32,0,32,8,0,8});
		}else{
			dWidth = 8;
			dHeight = 32;
			hitbox = new Polygon(new float[]{0,0,8,0,8,32,0,32});
		}
		genVisBox();
		
		solid = true;
		rotate = true;
		
		if(rotation == 0){
			originX = 0;
			originY = 4;
			this.x = x*Tile.TS;
			this.y = y*Tile.TS + Tile.TS/2;
		}else if(rotation == 1){
			originX = 4;
			originY = 0;
			this.x = x*Tile.TS + Tile.TS/2;
			this.y = y*Tile.TS;
		}else if(rotation == 2){
			originX = 32;
			originY = 4;
			this.x = x*Tile.TS + Tile.TS;
			this.y = y*Tile.TS + Tile.TS/2;
		}else if(rotation == 3){
			originX = 4;
			originY = 32;
			this.x = x*Tile.TS + Tile.TS/2;
			this.y = y*Tile.TS + Tile.TS;
		}
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
