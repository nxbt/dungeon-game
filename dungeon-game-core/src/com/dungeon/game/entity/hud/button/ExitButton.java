package com.dungeon.game.entity.hud.button;

import com.dungeon.game.entity.hud.window.Window;
import com.dungeon.game.world.World;

public class ExitButton extends Button {

	public Window window;
	
	public ExitButton(World world, Window window) {
		super(world, 0, 0, 14, 14, "exitButton.png");
		
		this.window = window;
	}
	
	public void calc() {
		this.x = window.x;
		this.y = window.y + window.d_height-14;
		super.calc();
	}
	
	@Override
	public void click() {
		window.close();
	}

	@Override
	public void post() {
		
	}

}
