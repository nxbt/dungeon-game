package com.dungeon.game.entity.particle;

import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public abstract class Particle extends Static {
	
	protected int duration;
	
	protected float dx;
	
	protected float dy;

	public Particle(World world, float x, float y, int duration, float dx, float dy) {
		super(world, x, y, 32, 32, "slot.png");
		this.duration = duration;
		layer = 4;
		clickable = false;
		
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		calc();
	}

	@Override
	public void calc() {
		x+=dx;
		y+=dy;
		duration--;
		if(duration == 0)killMe = true;

	}

}
