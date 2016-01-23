package com.dungeon.game.item;

import com.dungeon.game.world.World;

public class RubberSword extends Sword {

	public RubberSword(World world) {
		super(world, 1, 0, 0);
		name = "Rubber Sword";
		desc = "The Blade of Knocking! \n Has 'other' uses. ;) \n\n Damage: "+damage+"\n Cooldown: "+cooldown;
		knockratio = 0.3f;
		knockstr = 15;
	}

}
