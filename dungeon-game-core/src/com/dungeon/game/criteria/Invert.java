package com.dungeon.game.criteria;

import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;

public class Invert extends Criteria {
	private Criteria criteria;
  
	public Invert(World world, Criteria criteria){
		super(world);
		this.criteria = criteria;
	}
  
	public boolean metCriteria(){
		return !criteria.metCriteria();
	}
}
