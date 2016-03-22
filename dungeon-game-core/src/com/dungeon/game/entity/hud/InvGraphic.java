package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.world.World;

public class InvGraphic extends Window {
	
	public Inventory inv;

	public InvGraphic(World world, Inventory inv, float x, float y) {
		super(world, x, y);
		d_width = 0;
		d_height = 14;
		this.inv = inv;
		for(Slot slot: inv.slot){
			if(slot.x + 40>d_width) d_width = (int) (slot.x + 40);
			if(slot.y + 50>d_height) d_height = (int) (slot.y + 50);
		}
	}

	@Override
	public void post() {
		
	}
	
	protected void subCalc(){
		inv.update();
	}
	protected void subHovered(){
		inv.hovered();
	}
	protected void subDraw(SpriteBatch batch){
		for(Slot s: inv.slot) {
			s.draw(batch, (int)x, (int)y);
		}
	}

}
