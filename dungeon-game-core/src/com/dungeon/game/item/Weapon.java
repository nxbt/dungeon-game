package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.MeleeGraphic;
import com.dungeon.game.entity.WeaponGraphic;

public abstract class Weapon extends Item{
	public int damage;
	protected int cooldown;
	protected int speed;
	public WeaponGraphic graphic;
	
	public int stage;
	protected int stageTimer;
	
	public Weapon(int damage, int cooldown, int speed, Texture texture){
		super();
		maxStack = 1;
		this.damage = damage;
		this.cooldown = cooldown;
		type = WEAPON;
		sprite = texture;
	}
	
	protected void changeSprite(Texture texture){
		sprite = texture;
		graphic.sprite = sprite;
	}
	
	public abstract int[] getPos(boolean mousedown, boolean mousepressed);
	public abstract boolean isInUse();
}
