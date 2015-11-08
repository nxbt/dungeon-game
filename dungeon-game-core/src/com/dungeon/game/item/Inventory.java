package com.dungeon.game.item;

import com.dungeon.game.entity.InvGraphic;
import com.dungeon.game.world.World;

public class Inventory {
	public Slot[] slot;
	
	public InvGraphic graphic;
	
	public Inventory(int[][] layout, String graphic) {
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(layout[i], this);
		}
		
		this.graphic = new InvGraphic(graphic, this);
	}
	
	public void calc(World world) {
		for(Slot s: slot) {
			s.update(world);
		}
	}
	
	public void update(World world) {
		calc(world);
	}
}
