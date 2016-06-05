package com.dungeon.game.item;

import com.dungeon.game.world.World;

public class Gold extends Item {

	public Gold(World world) {
		super(world, "coin.png");
		
		type = DEFAULT;
		
		maxStack = Integer.MAX_VALUE;
		
		name = "Gold";
		desc = "Used to buy items and services.";
	}
	
	public String getDesc() {
		return "A small, round gold coin with a face inscribed on each sides. One face is of a smiling woman with long, flowing hair. The other face is of a stern looking man with a full beard. Although their origin is unknown, coins like this one can be found throughout every dungeon. Can be used to purchase items and services from merchants. Picking up a coin item will add it into your coin purse, where it can no longer be acessed directly.\n\n\"With enough of these, a man can acomplish anything\" -Kurson, fabled merchant";
	}
}
