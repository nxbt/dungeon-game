package com.dungeon.game.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class Slot {

	public Item item;
	
	public int type;
	
	public int x;
	public int y;
	
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
		if(Gdx.input.isButtonPressed(0) && world.mouse.x > x+inv.graphic.x && world.mouse.x < x+Item.SIZE+inv.graphic.x && world.mouse.y > y+inv.graphic.y && world.mouse.y < y+Item.SIZE+inv.graphic.y) {
			swap(world.mouse.slot);
		}
	}
	
	public void update(World world) {
		calc(world);
	}
	
	public void draw(SpriteBatch batch, int xoff, int yoff) {
		if(item != null) batch.draw(item.sprite, x+xoff, y+yoff, Item.SIZE, Item.SIZE);
	}
}
