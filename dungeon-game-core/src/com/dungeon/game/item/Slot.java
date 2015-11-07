package com.dungeon.game.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Slot {

	public Item item;
	
	public int type;
	
	public Slot(int type) {
		this.item = null;
		
		this.type = type;
	}
	
	public void swap(Slot that) {
		Item temp = that.item;
		
		that.item = this.item;
		this.item = temp;
	}
	
	public void draw(SpriteBatch batch, int x, int y) {
		if(item != null) batch.draw(item.sprite, x, y);
	}
}
