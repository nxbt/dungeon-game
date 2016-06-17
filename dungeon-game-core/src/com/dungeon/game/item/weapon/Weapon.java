package com.dungeon.game.item.weapon;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.HandheldGraphic;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.world.World;

public abstract class Weapon extends Hand{
	
	public int stage;
	
	protected int stageTimer;
	
	public Weapon(World world, String filename){
		super(world, filename);
		
		maxStack = 1;
	}
	
	protected void changeSprite(int index){
		sprite = textures[index];
		if(graphic != null) graphic.sprite = sprite;
	}
	
	public abstract float[] getPos(boolean mousedown, boolean mousepressed);
	
	public abstract boolean isInUse();
}
