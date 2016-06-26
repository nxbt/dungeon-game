package com.dungeon.game.item.weapon;

import java.util.ArrayList;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.world.World;

public abstract class Weapon extends Hand{
	
	public int stage;
	
	protected int stageTimer;
	
	public float weight;
	
	public Weapon(World world, String filename){
		super(world, filename);
		
		
	}
	
	protected void changeSprite(int index){
		sprite = textures[index];
		if(graphic != null) graphic.sprite = sprite;
	}
	
	public abstract ArrayList<Effect> getEffects();
}
