package com.dungeon.game.item;

public class Inventory {
	public Slot[] slot;
	
	public Inventory(int len) {
		slot = new Slot[len];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(0);
		}
	}
	
}
