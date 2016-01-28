package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class MenuButton extends Button {
	
	public MenuButton(World world, int x, int y) {
		super(world, x, y);
		
		sprite = new Texture("invButton.png");
		
		this.d_width = sprite.getWidth();
		this.d_height = sprite.getHeight();
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
