package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Arrow extends Ammo {

	public Arrow(){
		
	}

	@Override
	public void init() {
		name = "Arrow";
		desc = "The better to pew pew you with.";
		sprite = new Texture("Arrow.png");
		
		
	}
}
