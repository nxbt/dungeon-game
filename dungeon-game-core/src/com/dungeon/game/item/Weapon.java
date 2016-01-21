package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.entity.WeaponGraphic;
import com.dungeon.game.world.World;
import com.dungeon.game.entity.Character;

public abstract class Weapon extends Equipable{
	public int damage;
	protected int cooldown;
	protected int speed;
	public WeaponGraphic graphic;
	
	public int stage;
	protected int stageTimer;
	
	public Character owner;
	
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
	public abstract void hit(Character e, Projectile projectile);
	
	public void equip(World world, Character e){
		this.owner = e;
		world.entities.add(this.graphic);
	}
	
	public void unequip(World world, Character e){
		this.owner = null;
		world.entities.remove(this.graphic);
	}
}
