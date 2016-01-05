package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.WeaponGraphic;

public abstract class Weapon extends Item{
	protected int damage;
	protected int cooldown;
	public WeaponGraphic graphic;
	
	public Weapon(int damage, int cooldown, Texture texture){
		super();
		this.damage = damage;
		this.cooldown = cooldown;
		type = WEAPON;
		sprite = texture;
		graphic = new WeaponGraphic(sprite,10,10);
	}
}
