package com.dungeon.game.entity;

import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	
	public Projectile(int x, int y, float angle, float power) {
		super(x, y);
		dx = (float) Math.cos(angle/180*Math.PI);
		dy = (float) Math.sin(angle/180*Math.PI);
		this.angle = angle;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void calc(World world) {
		// TODO Auto-generated method stub

	}

}
