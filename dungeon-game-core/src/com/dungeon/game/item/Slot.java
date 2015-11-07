package com.dungeon.game.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Slot {

	public Item item;
	
	public int type;
	
	public int x;
	public int y;
	
	public Slot(int[] data) {
		this.item = null;
		
		this.type = data[0];
		
		this.x = data[1];
		this.y = data[2];
	}
	
	public void swap(Slot that) {
		Item temp = that.item;
		
		that.item = this.item;
		this.item = temp;
	}
	
	public void draw(SpriteBatch batch, int xoff, int yoff) {
		if(item != null) batch.draw(item.sprite, x+xoff, y+yoff);
	}
}
