package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class TrainingWindow extends Window {
	
	private HudSlot slot;

	public TrainingWindow(World world, float x, float y) {
		super(world, x, y);
		
		d_width = 200;
		d_height = 200;
		
		slot = new HudSlot(world,0,0,new Slot(world, new int[]{Item.HAND, 0, 0}, null));
	}
	
	public void subCalc(){
		this.slot.x = x + 32;
		this.slot.y = y + 32;
		this.slot.calc();
	}
	
	public void subHovered(){
		if(world.mouse.x > this.slot.x && world.mouse.x < this.slot.x + this.slot.d_width && world.mouse.y > this.slot.y && world.mouse.y < this.slot.y + this.slot.d_height){
			slot.hovered();
		}
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}
	
	public void subDraw(SpriteBatch batch){
		slot.draw(batch);
	}

}
