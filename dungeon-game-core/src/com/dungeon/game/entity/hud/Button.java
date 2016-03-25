package com.dungeon.game.entity.hud;

import com.dungeon.game.world.World;

public abstract class Button extends Hud {

	public Button(World world, float x, float y, int width, int height, String filename){
		super(world, x, y, width, height, filename);
	}
	
	public void hovered() {
		if(world.mouse.lb_pressed) {
			click();
		}
	}
	
	public abstract void click();

}
