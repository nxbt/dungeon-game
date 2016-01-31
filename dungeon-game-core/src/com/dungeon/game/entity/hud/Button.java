package com.dungeon.game.entity.hud;

import com.dungeon.game.world.World;

public abstract class Button extends Hud {

	public Button(World world, float x, float y){
		super(world, x, y);
	}
	
	public void hovered() {
		if(world.mouse.lb_pressed) {
			click();
		}
	}
	
	public abstract void click();

}
