package com.dungeon.game.criteria;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class HasGold extends Criteria{
	private int amount;
	private Character character;
  
	public HasGold(World world, int amount, Character character){
		super(world);
		this.amount = amount;
		this.character = character;
	}
  
	public boolean metCriteria(){
		return character.gold>=amount;
	}
}
