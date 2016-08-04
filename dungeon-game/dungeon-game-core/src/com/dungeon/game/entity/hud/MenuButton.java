package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class MenuButton extends Button {
	
	public MenuButton(World world, float x, float y) {
		super(world, x, y, 16, 16, "menuButton.png");
		
		sprite = new Texture("menuButton.png");
		
		this.d_width = sprite.getWidth();
		this.d_height = sprite.getHeight();
	}

	@Override
	public void click() {}

	@Override
	public void calc() {
		super.calc();
	}

	@Override
	public void post() {}

}
