package com.dungeon.game.item;

import com.dungeon.game.world.World;

public class Crap extends Item {

	public Crap(World world) {
		super(world, "crap.png");
		
		type = DEFAULT;
		
		name = "Pile of crap";
		desc = "A large lump of human excrement. Why would you pick this up?";
		
		maxStack = 10;
	}
	
	public String getDesc() {
		return "It's crap, what did you expect? \n\n\n\n\n\n\nNOTHING WILL HAPPEN IF YOU EAT IT!";
	}

}
