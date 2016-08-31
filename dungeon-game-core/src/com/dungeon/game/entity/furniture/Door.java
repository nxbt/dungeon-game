package com.dungeon.game.entity.furniture;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Door extends Static {
	
	private boolean open;
	
	public Door(World world, LockedDoor door){
		super(world, door.x, door.y, 32, 32, "slot.png");
		construct(door.tileX, door.tileY, door.rotation);
		open = true;
		solid = false;
		this.sprite = new com.dungeon.game.textures.entity.Door(door.rotation, false).texture;
	}
	
	public Door(World world, float x, float y, int rotation) {
		super(world, x, y, 32, 32, "slot.png");
		construct(x, y, rotation);
		this.sprite = new com.dungeon.game.textures.entity.Door(rotation, false).texture;
	}
	
	public Door(World world, float x, float y, int rotation, boolean isLocked) {
		super(world, x, y, 32, 32, "slot.png");
		construct(x, y, rotation);
		this.sprite = new com.dungeon.game.textures.entity.Door(rotation, isLocked).texture;
	}

	public void construct(float x, float y, int rotation) {
		
		layer = ROOF;
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
	
	public void toggle(){
		open = !open;
		if(open) solid = false;
	}

	@Override
	public void calc() {
		if(open && angle < 90) angle+=10;
		else if(!open && angle > 0) angle-=10;
		
		if(angle == 0) solid = true;
	}

	@Override
	public void post() {
		
	}
	
	public void hovered(){
		if(world.mouse.lb_pressed)toggle();
	}
	
}
