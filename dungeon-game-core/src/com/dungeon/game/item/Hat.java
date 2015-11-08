package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Hat extends Item {
	public Hat() {}
	
	@Override
	public void init() {
		type = HELM;
		
		name = "A hat";
		desc = "As opossed to The Hat.";
		
		sprite = new Texture("hat.png");
		
		maxStack = 1;
		
		stack = 1;
	}
}
