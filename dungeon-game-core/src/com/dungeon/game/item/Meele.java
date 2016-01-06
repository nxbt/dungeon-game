package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.WeaponGraphic;

public abstract class Meele extends Weapon {

	private WeaponGraphic hitbox;
	
	protected int stage;
	
	protected int stageTimer;
	
	public Meele(int damage, int cooldown,int speed, Texture texture) {
		super(damage, cooldown, speed, texture);
		// TODO Auto-generated constructor stub
	}
	
	

}
