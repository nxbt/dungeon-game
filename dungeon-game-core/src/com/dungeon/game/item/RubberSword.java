package com.dungeon.game.item;

public class RubberSword extends Sword {

	public RubberSword() {
		super(1, 0, 0);
		name = "Rubber Sword";
		desc = "The Blade of Knocking! \n Has 'other' uses. ;) \n\n Damage: "+damage+"\n Cooldown: "+cooldown;
		knockratio = 0.3f;
		knockstr = 20;
	}

}
