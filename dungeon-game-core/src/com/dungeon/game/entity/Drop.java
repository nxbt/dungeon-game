package com.dungeon.game.entity;

import com.dungeon.game.item.Crap;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.Tile;
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
		int tile_lt = (int) (x/Tile.TS);
		int tile_dn = (int) (y/Tile.TS);
		int tile_rt = (int) ((x+Item.SIZE)/Tile.TS);
		int tile_up = (int) ((y+Item.SIZE)/Tile.TS);
		
		boolean dl = world.curFloor.tm[tile_dn][tile_lt].data == 1;
		boolean dr = world.curFloor.tm[tile_dn][tile_rt].data == 1;
		boolean ul = world.curFloor.tm[tile_up][tile_lt].data == 1;
		boolean ur = world.curFloor.tm[tile_up][tile_rt].data == 1;
		
		if(dl && dr) {
			y = (tile_dn+1) * Tile.TS;
			
			dl = false;
			dr = false;
		}
		if(ul && ur) {
			y = (tile_up * Tile.TS)-Item.SIZE;
			
			ul = false;
			ur = false;
		}
		if(dl && ul) {
			x = (tile_lt+1) * Tile.TS;
			
			dl = false;
			ul = false;
		}
		if(dr && ur) {
			x = (tile_rt * Tile.TS)-Item.SIZE;
			
			ul = false;
			ur = false;
		}
		
		if(dl) {
			if((tile_lt+1)*Tile.TS - this.x < (tile_dn+1)*Tile.TS - this.y) {
				x = (tile_lt+1) * Tile.TS;
			}
			else {
				y = (tile_dn+1) * Tile.TS;
			}
		}
		if(dr) {
			if(x+Item.SIZE - tile_rt*Tile.TS < (tile_dn+1)*Tile.TS - this.y) {
				x = (tile_rt * Tile.TS)-Item.SIZE;
			}
			else {
				y = (tile_dn+1) * Tile.TS;
			}
		}
		if(ul) {
			if((tile_lt+1)*Tile.TS - this.x < y+Item.SIZE - tile_up*Tile.TS) {
				x = (tile_lt+1) * Tile.TS;
			}
			else {
				y = (tile_up * Tile.TS)-Item.SIZE;
			}
		}
		if(ur) {
			if(x+Item.SIZE - tile_rt*Tile.TS < y+Item.SIZE - tile_up*Tile.TS) {
				x = (tile_rt * Tile.TS)-Item.SIZE;
			}
			else {
				y = (tile_up * Tile.TS)-Item.SIZE;
			}
		}
		
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
