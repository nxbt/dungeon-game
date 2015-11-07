package com.dungeon.game.item;

import com.dungeon.game.entity.InvGraphic;

public class Inventory {
	public Slot[] slot;
	
	public InvGraphic graphic;
	
	public Inventory(int[][] layout) {
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(layout[i]);
		}
		
		graphic = new InvGraphic("badlogic.jpg", slot);
	}
	
}
