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
	
	public void hovered(World world) {
		for(Slot s: slot) {
			if(s.x + graphic.x < world.mouse.x && s.x + graphic.x + Item.SIZE > world.mouse.x && s.y + graphic.y < world.mouse.y && s.y + graphic.y + Item.SIZE > world.mouse.y) s.hovered(world);
		}
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
