package com.dungeon.game.item;

import com.dungeon.game.entity.hud.InvGraphic;
import com.dungeon.game.world.World;

public class Inventory {
	public Slot[] slot;
	
	public InvGraphic graphic;
	
	public Inventory(int[][] layout, String graphic, int x, int y) {
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(layout[i], this);
		}
		
		this.graphic = new InvGraphic(graphic, this, x, y);
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
