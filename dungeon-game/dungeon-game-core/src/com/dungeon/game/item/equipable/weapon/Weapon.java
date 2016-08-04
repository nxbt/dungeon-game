package com.dungeon.game.item.equipable.weapon;

import java.util.ArrayList;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.equipable.weapon.part.Part;
import com.dungeon.game.world.World;

public abstract class Weapon extends Hand{
	
	public int stage;
	
	public int stageTimer;
	
	public float weight;
	
	public ArrayList<Effect> hitEffects;
	
	public Weapon(World world, String filename){
		super(world, filename);
		
		hitEffects = new ArrayList<Effect>();
	}
	
	protected void changeSprite(int index){
		sprite = textures[index];
		if(graphic != null) graphic.sprite = sprite;
	}
	
	public ArrayList<Effect> getEffects() {
		ArrayList<Effect> effects = new ArrayList<Effect>();
		
		for(Effect effect: hitEffects) {
			effects.add(effect.clone());
		}
		
		return effects;
	}
	
	public abstract Part[] getParts();
}