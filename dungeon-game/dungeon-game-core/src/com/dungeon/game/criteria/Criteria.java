package com.dungeon.game.criteria;

import com.dungeon.game.world.World;

public abstract class Criteria{
	protected World world;
  
	public Criteria(World world){
		this.world = world;
	}
  
	public abstract boolean metCriteria();
}
