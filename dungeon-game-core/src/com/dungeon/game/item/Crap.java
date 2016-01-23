package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class Crap extends Item {

	public Crap(World world) {
		super(world);
	}

	@Override
	public void init() {
		type = DEFAULT;
		
		name = "Pile of crap";
		desc = "A large lump of human excrement. Why would you pick this up?";
		
		sprite = new Texture("Crap.png");
		
		maxStack = 10;
	}

}
