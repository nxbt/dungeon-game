package com.dungeon.game.item.weapon.part.sword.guard;

public class BasicGuard extends SwordGuard {

	public BasicGuard() {
		super("Basic Guard", SPIRTES[0]);
		id = 0;
		dmgMult = 1;
		speedMult = 1;
		knockMult = 1;
		weightMult = 1;
		allowedSwings = new String[0];
		bannedSwings = new String[0];
	}

}
