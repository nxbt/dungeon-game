package com.dungeon.game.entity.hud.button;

import com.dungeon.game.world.World;

public class HelpButton extends Button {
	
	public HelpButton(World world, float x, float y) {
		super(world, x, y, 16, 16, "helpButton.png");
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
