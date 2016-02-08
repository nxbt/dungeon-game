package com.dungeon.game.criteria;

import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;

public class HasGold extends Criteria{
	private int amount;
	private Character character;
	boolean isTrue;
  
	public HasGold(World world, int amount, Character character, boolean isTrue){
		super(world);
		this.amount = amount;
		this.character = character;
		this.isTrue = isTrue;
	}
  
	public boolean metCriteria(){
		if(isTrue)return character.gold>=amount;
		else return !(character.gold>=amount);
	}
}
