package com.dungeon.game.item.equipable;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.HandheldGraphic;
import com.dungeon.game.world.World;

public abstract class Hand extends Equipable {
	

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
		graphic.getBody(world.curFloor.box2dWorld);
		graphic.bodyMade = true;
		
		this.owner = owner;
		
		graphic.sprite = sprite;
		if(leftSide){
			graphic.toFlip = true;
			graphic.flipped = false;
		}else{
			graphic.toFlip = true;
			graphic.flipped = false;
		}
		
		this.leftSide = leftSide;
		
		world.entities.add(world.entities.indexOf(owner)+1,this.graphic);

		onEquip();
	}
	
	public void unequip(){
		reset();
		world.curFloor.box2dWorld.destroyBody(graphic.body);
		graphic.body = null;
		graphic.bodyMade = false;

		onUnequip();
		
		this.owner = null;
		
		world.entities.remove(this.graphic);
	}
	

	
	public abstract float[] getPos(boolean mousedown, boolean mousepressed);
	
	public abstract void reset();
	
	public abstract boolean isInUse();

}
