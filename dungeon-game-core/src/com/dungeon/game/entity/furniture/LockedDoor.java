package com.dungeon.game.entity.furniture;

import com.dungeon.game.item.Key;
import com.dungeon.game.world.World;

public class LockedDoor extends Door {
	
	public int rotation;
	
	public int tileX;
	public int tileY;

	public LockedDoor(World world, float x, float y, int rotation) {
		super(world, x, y, rotation);
		this.rotation = rotation;
		tileX = (int) x;
		tileY = (int) y;
	}
	

	
	public void hovered(){
		if(world.mouse.lb_pressed && world.mouse.slot.item != null && world.mouse.slot.item instanceof Key){
			unlock();
			world.mouse.slot.item.stack--;
			if(world.mouse.slot.item.stack == 0) world.mouse.slot.item = null;
		}
	}
	
	public void unlock(){
		killMe = true;
		world.entities.add(new Door(world, this));
	}

}
