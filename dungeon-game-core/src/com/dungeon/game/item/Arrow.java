package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Arrow extends Ammo {

	public Arrow(World world){
		super(world);
	}

	@Override
	public void init() {
		name = "Arrow";
		desc = "The better to pew pew you with.";
		sprite = new Texture("Arrow.png");
		
		
	}
}
