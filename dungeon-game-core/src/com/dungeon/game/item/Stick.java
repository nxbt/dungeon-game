package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Stick extends Item {

	public Stick() {}

	@Override
	public void init() {
		type = 0;
		
		name = "Wooden stick";
		desc = "As opossed to a stone stick.";
		
		sprite = new Texture("stick.png");
		
		maxStack = 10;
		
		stack = 1;
	}

}
