package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.WeaponGraphic;

public abstract class Weapon extends Item{
	public int damage;
	protected int cooldown;
	protected int speed;
	public WeaponGraphic graphic;
	
	public Weapon(int damage, int cooldown, int speed, Texture texture){
		super();
		maxStack = 1;
		this.damage = damage;
		this.cooldown = cooldown;
		type = WEAPON;
		sprite = texture;
		graphic = new WeaponGraphic(sprite,32,32, this, new Polygon(new float[]{Item.SIZE*0.6f,Item.SIZE*0.2f,Item.SIZE*0.8f,Item.SIZE*0.4f,0,Item.SIZE*1.1f,Item.SIZE*0.1f,Item.SIZE}), 0.9f, 0.1f);
	}
	
	public abstract boolean isInUse();
}
