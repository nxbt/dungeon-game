package com.dungeon.game.criteria;

import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;

public class Invert extends Criteria {
  
	public Invert(World world, Criteria criteria){
		super(world);
	}
  
	public boolean metCriteria(){
		return !criteria.metCriteria();
	}
}
