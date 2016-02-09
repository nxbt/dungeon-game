package com.dungeon.game.item;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Projectile;
import com.dungeon.game.entity.WeaponGraphic;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;
import com.dungeon.game.effect.Effect;

public abstract class Weapon extends Equipable{
	public WeaponGraphic graphic;
	
	public int stage;
	
	protected int stageTimer;
	
	public Character owner;
	
	public float distance=0;
	public float polarAngle= 0;
	public float angle=0;
	
	public boolean leftSide;
	
	public Weapon(World world, Texture texture){
		super(world);
		
		maxStack = 1;
		
		type = WEAPON;
		
		sprite = texture;
	}
	
	protected void changeSprite(Texture texture){
		sprite = texture;
		graphic.sprite = sprite;
	}
	
	public void equip(Character owner, boolean leftSide){
		reset();
		
		this.owner = owner;
		
		world.entities.add(this.graphic);
	}
	
	public void unequip(){
		reset();
		
		this.owner = null;
		
		world.entities.remove(this.graphic);
	}
	
	public abstract float[] getPos(boolean mousedown, boolean mousepressed);
	
	public abstract boolean isInUse();
	
	public abstract void reset();
}
