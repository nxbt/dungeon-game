package com.dungeon.game.item.weapon;

import com.dungeon.game.world.World;

public class RubberSword extends Sword {

	public RubberSword(World world) {
		super(world, 1, 1);
		name = "Rubber Sword";
		desc = "The Blade of Knocking! \n Has 'other' uses. ;) \n\n Damage: "+damage;
		knockratio = 0.3f;
		knockstr = 15;
	}
	
	public String getDesc() {
		return "Double_Meaning.exe is not responding. \n\n " + desc;
	}

}
