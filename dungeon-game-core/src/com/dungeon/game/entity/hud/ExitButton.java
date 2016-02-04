package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class ExitButton extends Button {

	public Window window;
	
	public ExitButton(World world, Window window) {
		super(world, 0, 0);
		
		this.window = window;
		
		sprite = new Texture("exitButton.png");
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
	}
	
	public void calc() {
		this.x = window.x;
		this.y = window.y + window.d_height-14;
	}
	
	@Override
	public void click() {
		window.close();
	}

	@Override
	public void post() {
		
	}

}
