package com.dungeon.game.entity.hud;

import com.dungeon.game.world.World;

public class HoverZone extends Hud {
	
	private String text;

	public HoverZone(World world, float x, float y, int width, int height, String text) {
		super(world, x, y, width, height, "slot.png");
		this.text = text;
	}

	@Override
	public void calc() {}
	
	public void hovered(){
		world.descBox.updateText(text);
	}

	@Override
	public void post() {}

}
