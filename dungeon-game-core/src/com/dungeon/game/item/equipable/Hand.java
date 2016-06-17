package com.dungeon.game.item.equipable;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.HandheldGraphic;
import com.dungeon.game.world.World;

public abstract class Hand extends Equipable {

	
	public Character owner;
	

	public HandheldGraphic graphic;
	
	public float distance=0;
	public float polarAngle= 0;
	public float angle=0;
	
	public boolean leftSide;

	public Hand(World world, String filename) {
		super(world, filename);
		
		name = "Hand Held Item";
		
		physc_resist = 1;
		flame_resist = -1;
		
		type = HAND;
		
		desc = "An Item held in the hand\n\n Armor: " + (int)physc_resist;

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
	

	
	public abstract void reset();

}
