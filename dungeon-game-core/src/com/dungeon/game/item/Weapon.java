package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.entity.WeaponGraphic;
import com.dungeon.game.world.World;

public abstract class Weapon extends Equipable{
	public int damage;
	protected int cooldown;
	protected int speed;
	public WeaponGraphic graphic;
	
	public int stage;
	protected int stageTimer;
	
	public Dynamic owner;
	
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
	
	public abstract float[] getPos(boolean mousedown, boolean mousepressed);
	public abstract boolean isInUse();
	public abstract void hit(Dynamic e, Projectile projectile);
	
	public void equip(World world, Dynamic e){
		this.owner = e;
		System.out.println(owner.name);
		System.out.println(graphic.weapon.owner);
		world.entities.add(this.graphic);
	}
	
	public void unequip(World world, Dynamic e){
		this.owner = null;
		world.entities.remove(this.graphic);
	}
}
