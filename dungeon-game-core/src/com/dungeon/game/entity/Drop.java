package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.hud.DescWindow;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class Drop extends Static {
	
	Slot slot;
	
	public Drop(World world, float x, float y, Slot slot) {
		super(world, x, y);
		
		this.slot = new Slot(world, new int[] {0,0,0}, null);
		
		this.slot.swap(slot);
		
		sprite = this.slot.item.sprite;

		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		hitbox = new Polygon(new float[]{4,4,28,4,28,28,4,28});
		
		origin_x =16;
		origin_y = 16;
		
		angle = (float) (180f-Math.random()*360);
	}

	@Override
	public void init() {}
	
	@Override
	public void hovered(){
		if(world.mouse.canPickup) {
			if(world.mouse.shift_down) {
				world.descBox.updateText(slot.item);
				
				if(world.mouse.lb_pressed) {
					slot.item = world.player.inv.addItem(slot.item);
				}
				else if(world.mouse.rb_pressed && slot.item != null) {
					DescWindow temp = new DescWindow(world, world.mouse.x, world.mouse.y);
					temp.updateText(slot.item);
					temp.open();
				}
			}
			else if(world.mouse.lb_pressed) {
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
					world.mouse.slot.item = slot.item;
					slot.item = world.mouse.slot.item.clone();
					slot.item.stack--;
					world.mouse.slot.item.stack = 1;
				}
				else slot.swap(world.mouse.slot);
				if(slot.item != null && slot.item.stack == 0) slot.item = null;
				if(world.mouse.slot.item != null && world.mouse.slot.item.stack == 0) world.mouse.slot.item = null;
			}
		}
		
		if(slot.item == null) killMe = true;
		else sprite = slot.item.sprite;
	}

	@Override
	public void calc() {
//		int tile_lt = (int) ((x-origin_x)/Tile.TS);
//		int tile_dn = (int) ((y-origin_y)/Tile.TS);
//		int tile_rt = (int) (((x-origin_x)+Item.SIZE)/Tile.TS);
//		int tile_up = (int) (((y-origin_y)+Item.SIZE)/Tile.TS);
//		
//		boolean dl = world.curFloor.tm[tile_dn][tile_lt].data == 1;
//		boolean dr = world.curFloor.tm[tile_dn][tile_rt].data == 1;
//		boolean ul = world.curFloor.tm[tile_up][tile_lt].data == 1;
//		boolean ur = world.curFloor.tm[tile_up][tile_rt].data == 1;
//		
//		if(dl && dr) {
//			y = (tile_dn+1) * Tile.TS;
//			
//			dl = false;
//			dr = false;
//		}
//		if(ul && ur) {
//			y = (tile_up * Tile.TS)-Item.SIZE;
//			
//			ul = false;
//			ur = false;
//		}
//		if(dl && ul) {
//			x = (tile_lt+1) * Tile.TS;
//			
//			dl = false;
//			ul = false;
//		}
//		if(dr && ur) {
//			x = (tile_rt * Tile.TS)-Item.SIZE;
//			
//			ul = false;
//			ur = false;
//		}
//		
//		if(dl) {
//			if((tile_lt+1)*Tile.TS - this.x < (tile_dn+1)*Tile.TS - this.y) {
//				x = (tile_lt+1) * Tile.TS;
//			}
//			else {
//				y = (tile_dn+1) * Tile.TS;
//			}
//		}
//		if(dr) {
//			if(x+Item.SIZE - tile_rt*Tile.TS < (tile_dn+1)*Tile.TS - this.y) {
//				x = (tile_rt * Tile.TS)-Item.SIZE;
//			}
//			else {
//				y = (tile_dn+1) * Tile.TS;
//			}
//		}
//		if(ul) {
//			if((tile_lt+1)*Tile.TS - this.x < y+Item.SIZE - tile_up*Tile.TS) {
//				x = (tile_lt+1) * Tile.TS;
//			}
//			else {
//				y = (tile_up * Tile.TS)-Item.SIZE;
//			}
//		}
//		if(ur) {
//			if(x+Item.SIZE - tile_rt*Tile.TS < y+Item.SIZE - tile_up*Tile.TS) {
//				x = (tile_rt * Tile.TS)-Item.SIZE;
//			}
//			else {
//				y = (tile_up * Tile.TS)-Item.SIZE;
//			}
//		}
	}

	public void post() {}
}
