package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Stick extends Item {

	public Stick() {}

	@Override
	public void init() {
		type = DEFAULT;
		
		name = "Wooden stick";
		desc = "As opossed to a stone stick.";
		
		sprite = new Texture("stick.png");
		
		maxStack = 10;
	}

}
