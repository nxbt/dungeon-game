package com.dungeon.game.entity;

import com.dungeon.game.world.World;

public class Goon extends Enemy {

	public Goon(int x, int y) {
		super(x, y);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calc(World world) {
		findPath(world.curFloor.tm, new float[]{world.player.x,world.player.y});
		
	}

}
