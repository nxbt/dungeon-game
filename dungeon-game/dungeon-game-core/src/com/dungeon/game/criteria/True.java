package com.dungeon.game.criteria;

import com.dungeon.game.world.World;

public class True extends Criteria {

	public True(World world) {
		super(world);
	}

	@Override
	public boolean metCriteria() {
		return true;
	}

}
