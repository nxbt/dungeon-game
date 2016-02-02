package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Gold extends Item {

	public Gold(World world) {
		super(world);
		
		type = DEFAULT;
		
		maxStack = Integer.MAX_VALUE;
		
		sprite = new Texture("coin.png");
		
		name = "Gold";
		desc = "Show me da money";
	}

	@Override
	public void init() {

	}

}
