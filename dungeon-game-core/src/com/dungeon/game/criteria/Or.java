package com.dungeon.game.criteria;

import com.dungeon.game.world.World;

public class Or extends Criteria {
  private Criteria[] criterias;
  
	public Or(World world, Criteria... criterias){
		super(world);
		this.criterias = criterias;
	}
  
	public boolean metCriteria(){
	  for(Criteria criteria: criterias){
	    if(criteria.metCriteria())return true;
	  }
		return false;
	}
}
