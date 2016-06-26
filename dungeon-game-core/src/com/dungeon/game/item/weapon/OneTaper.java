package com.dungeon.game.item.weapon;

import com.dungeon.game.world.World;

public class OneTaper extends Sword {

	public OneTaper(World world) {
		super(world, 1000, 5, 0);
		name = "One Tapper";
		desc = "This large sword is light and well balanced in your hand! \n ...'balanced' \n\n Damage: "+damage;
		knockratio = 0.3f;
		knockstr = 10;
	}
	
	public String getDesc() {
		return "Tap";
	}

}
