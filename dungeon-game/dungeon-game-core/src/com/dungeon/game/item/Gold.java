package com.dungeon.game.item;

import com.dungeon.game.world.World;

public class Gold extends Item {

	public Gold(World world) {
		super(world, "coin.png");
		
		type = DEFAULT;
		
		maxStack = Integer.MAX_VALUE;
		
		name = "Gold Coin";
		desc = "Used to buy items and services.";
	}
	
	public String getDesc() {
		return "Gold Coin\n  A small, round gold coin with a face inscribed on each sides. One face is of a smiling woman with long, flowing hair. The other face is of a stern looking man with "
				+ "a full beard. Although their origin is unknown, coins like this one can be found throughout every dungeon. Their strange ubiquity has led to them becoming the standard "
				+ "curreny throughout the dungeons.\n  Nearly all merchants will require these in exchange for goods and services, and will offer them to acquire the same. As such, any "
				+ "traveler should be sure to have a few on hand at all times, or they may find themselves in a critical state with no way to get help.\n\n\"With enough of these, a man can acomplish anything\" -Kurson, fabled merchant";
	}
}
