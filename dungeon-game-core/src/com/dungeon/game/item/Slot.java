package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class Slot {

	public Item item;
	
	public int type;
	
	public int x;
	public int y;
	
	private boolean hovered;
	
	private Inventory inv;
	
	public Slot(int[] data, Inventory inv) {
		this.item = null;
		
		this.type = data[0];
		
		this.x = data[1];
		this.y = data[2];
		
		this.inv = inv;
	}
	
	public void swap(Slot that) {
		Item temp = that.item;
		
		that.item = this.item;
		this.item = temp;
	}
	
	public void calc(World world) {
		hovered = false;
		if(world.mouse.x > x+inv.graphic.x && world.mouse.x < x+Item.SIZE+inv.graphic.x && world.mouse.y > y+inv.graphic.y && world.mouse.y < y+Item.SIZE+inv.graphic.y&&((world.mouse.slot.item==null?true:world.mouse.slot.item.type==type)||type==0)) {
			if(world.mouse.lb_pressed) {
				if(item != null && world.mouse.slot.item != null && world.mouse.slot.item.name.equals(item.name)) {
					if(item.stack + world.mouse.slot.item.stack <= item.maxStack) {
						item.stack+=world.mouse.slot.item.stack;
						world.mouse.slot.item = null;
					}
					else {
						world.mouse.slot.item.stack = item.stack+world.mouse.slot.item.stack-item.maxStack;
						item.stack = item.maxStack;
					}
				}
				else swap(world.mouse.slot);
			}
			else if(world.mouse.rb_pressed) {
				if(item != null && world.mouse.slot.item != null && world.mouse.slot.item.name.equals(item.name)) {
					if(world.mouse.slot.item.stack < item.maxStack) {
						item.stack--;
						world.mouse.slot.item.stack++;
					}
				}
				else if(item != null && world.mouse.slot.item == null) {
					item.stack--;
					world.mouse.slot.item = (Item) item.clone();
					world.mouse.slot.item.stack = 1;
				}
				else if(item == null && world.mouse.slot.item != null) {
					world.mouse.slot.item.stack--;
					item = (Item) world.mouse.slot.item.clone();
					item.stack = 1;
				}
				else swap(world.mouse.slot);
				if(item != null && item.stack == 0) item = null;
				if(world.mouse.slot.item != null && world.mouse.slot.item.stack == 0) world.mouse.slot.item = null;
			}
			if(world.mouse.slot.item == null) hovered = true;
		}
		
	}
	
	public void update(World world) {
		calc(world);
	}
	
	public void draw(SpriteBatch batch, int xoff, int yoff) {
		if(item != null) {
			batch.draw(item.sprite, x+xoff, y+yoff, Item.SIZE, Item.SIZE);
			
//			if(hovered) {
//				BitmapFont desc = new BitmapFont();
//				desc.setColor(Color.LIGHT_GRAY);
//				desc.getData().setScale(1f);
//				
//				desc.draw(batch, item.name, x+xoff, y+yoff+Item.SIZE);
//			}
			if(item.stack > 1) {
				BitmapFont font = new BitmapFont();
				font.setColor(Color.LIGHT_GRAY);
				font.getData().setScale(1f);
				
				font.draw(batch, Integer.toString(item.stack), (float) (x+xoff+Item.SIZE-font.getScaleX()*(Math.floor(Math.log10(item.stack))+ 1)*7)-2, y+yoff+font.getScaleY()*12+1);
			}
		}
	}
}
