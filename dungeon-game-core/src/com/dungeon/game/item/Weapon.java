package com.dungeon.game.item;

public abstract class Weapon extends Item{
	protected int damage;
	protected int cooldown;
	
	public Weapon(int damage, int cooldown){
		super();
		this.damage = damage;
		this.cooldown = cooldown;
		type = WEAPON;
	}
}
