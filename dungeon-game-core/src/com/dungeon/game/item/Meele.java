package com.dungeon.game.item;

import com.dungeon.game.entity.WeaponHitbox;

public abstract class Meele extends Weapon {

	private WeaponHitbox hitbox;
	private int swingTimer;
	public Meele(int damage, int cooldown) {
		super(damage, cooldown);
		// TODO Auto-generated constructor stub
	}
	
	

}
