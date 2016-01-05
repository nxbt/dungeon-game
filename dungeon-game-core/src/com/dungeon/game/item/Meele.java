package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.WeaponGraphic;

public abstract class Meele extends Weapon {

	private WeaponGraphic hitbox;
	private int swingTimer;
	public Meele(int damage, int cooldown, Texture texture) {
		super(damage, cooldown, texture);
		// TODO Auto-generated constructor stub
	}
	
	

}
