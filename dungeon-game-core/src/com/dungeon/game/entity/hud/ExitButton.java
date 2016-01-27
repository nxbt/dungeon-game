package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class ExitButton extends Button {

	public Window window;
	
	public ExitButton(World world, int x, int y, Window window) {
		super(world, x, y);
		
		this.window = window;
		
		sprite = new Texture("exitButton");
	}

	@Override
	public void click() {
		window.close();
	}

}
