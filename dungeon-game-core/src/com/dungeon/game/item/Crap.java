package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Crap extends Item {

	public Crap() {}

	@Override
	public void init() {
		type = DEFAULT;
		
		name = "Pile of crap";
		desc = "A large lump of human excrement. Why would you pick this up?\n\n Attack Damage: depends \n Range: depends";
		
		sprite = new Texture("Crap.png");
		
		maxStack = 10;
	}

}
