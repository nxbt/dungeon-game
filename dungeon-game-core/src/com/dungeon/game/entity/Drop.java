package com.dungeon.game.entity;

import com.dungeon.game.item.Crap;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class Drop extends Static {
	
	Slot slot;
	
	public Drop(int x, int y, Slot slot) {
		super(x, y);
		
		this.slot = new Slot(new int[] {0,0,0}, null);
		
		this.slot.swap(slot);
		
		sprite = this.slot.item.sprite;

		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
	}

	@Override
	public void init() {}

	@Override
	public void calc(World world) {
		if(world.mouse.canPickup && world.mouse.x > x-world.cam.x+world.cam.WIDTH/2 && world.mouse.x < x+Item.SIZE-world.cam.x+world.cam.WIDTH/2 && world.mouse.y > y-world.cam.y+world.cam.HEIGHT/2 && world.mouse.y < y+Item.SIZE-world.cam.y+world.cam.HEIGHT/2) {
			if(world.mouse.lb_pressed) {
				if(slot.item != null && world.mouse.slot.item != null && world.mouse.slot.item.name.equals(slot.item.name)) {
					if(slot.item.stack + world.mouse.slot.item.stack <= slot.item.maxStack) {
						slot.item.stack+=world.mouse.slot.item.stack;
						world.mouse.slot.item = null;
					}
					else {
						world.mouse.slot.item.stack = slot.item.stack+world.mouse.slot.item.stack-slot.item.maxStack;
						slot.item.stack = slot.item.maxStack;
					}
				}
				else slot.swap(world.mouse.slot);
			}
			else if(world.mouse.rb_pressed) {
				if(slot.item != null && world.mouse.slot.item != null && world.mouse.slot.item.name.equals(slot.item.name)) {
					if(world.mouse.slot.item.stack < slot.item.maxStack) {
						slot.item.stack--;
						world.mouse.slot.item.stack++;
					}
				}
				else if(slot.item != null && world.mouse.slot.item == null) {
					slot.item.stack--;
					world.mouse.slot.item = (Item) slot.item.clone();
					world.mouse.slot.item.stack = 1;
				}
				else if(slot.item == null && world.mouse.slot.item != null) {
					world.mouse.slot.item.stack--;
					slot.item = (Item) world.mouse.slot.item.clone();
					slot.item.stack = 1;
				}
				else slot.swap(world.mouse.slot);
				if(slot.item != null && slot.item.stack == 0) slot.item = null;
				if(world.mouse.slot.item != null && world.mouse.slot.item.stack == 0) world.mouse.slot.item = null;
			}
		}
		
		if(slot.item == null) killMe = true;
		else sprite = slot.item.sprite;
	}

}
