package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Stick extends Item {

	public Stick(World world) {
		super(world);
	}

	@Override
	public void init() {
		type = DEFAULT;
		
		name = "Wooden stick";
		desc = "As opossed to a stone stick.";
		
		sprite = new Texture("stick.png");
		
		maxStack = 10;
	}
	
	public String getDesc() {
		return "It's not the only brown and sticky thing in this game.";
	}

}
