package com.dungeon.game.entity.particle;

import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public abstract class Particle extends Static {
	
	protected int duration;
	
	protected Vector2 moveVec;

	public Particle(World world, float x, float y, int duration, float dx, float dy) {
		super(world, x, y, 32, 32, "slot.png");
		this.duration = duration;
		layer = 4;
		moveVec = new Vector2(dx, dy);
	}
	
	public void update() {
		calc();
	}

	@Override
	public void calc() {
		x+=moveVec.x;
		y+=moveVec.y;
		duration--;
		if(duration == 0)killMe = true;

	}

}
