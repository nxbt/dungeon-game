package com.dungeon.game.criteria;

import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;

public class And extends Criteria {
  private Criteria[] criterias;
  
	public And(World world, Criteria... criterias){
		super(world);
		this.criterias = criterias;
	}
  
	public boolean metCriteria(){
	  for(Criteria criteria: criterias){
	    if(!criteria.metCriteria())return false;
	  }
		return true;
	}
}
