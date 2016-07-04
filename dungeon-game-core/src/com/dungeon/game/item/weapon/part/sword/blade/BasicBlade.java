package com.dungeon.game.item.weapon.part.sword.blade;

import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.item.weapon.Weapon;

public class BasicBlade extends SwordBlade {

	public BasicBlade() {
		super("Basic Blade", SPIRTES[0]);
		id = 0;
		dmgMult = 1;
		speedMult = 1;
		knockMult = 1;
		weightMult = 1;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

}
