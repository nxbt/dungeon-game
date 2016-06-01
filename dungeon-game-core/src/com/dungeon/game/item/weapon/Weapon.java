package com.dungeon.game.item.weapon;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.WeaponGraphic;
import com.dungeon.game.item.Equipable;
import com.dungeon.game.world.World;

public abstract class Weapon extends Equipable{
	public WeaponGraphic graphic;
	
	public int stage;
	
	protected int stageTimer;
	
	public Character owner;
	
	public float distance=0;
	public float polarAngle= 0;
	public float angle=0;
	
	public boolean leftSide;
	
	public Weapon(World world, String filename){
		super(world, filename);
		
		maxStack = 1;
		
		type = WEAPON;
	}
	
	protected void changeSprite(int index){
		sprite = textures[index];
		if(graphic != null) graphic.sprite = sprite;
	}
	
	public void equip(Character owner, boolean leftSide){
		reset();
		
		this.owner = owner;
		
		this.leftSide = leftSide;
		
		world.entities.add(world.entities.indexOf(owner)+1,this.graphic);
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
