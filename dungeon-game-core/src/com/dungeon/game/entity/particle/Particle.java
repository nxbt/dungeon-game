package com.dungeon.game.entity.particle;

import com.dungeon.game.entity.Static;
import com.dungeon.game.world.World;

public abstract class Particle extends Static {
	
	protected int duration;
	
	protected float dx;
	
	protected float dy;

	public Particle() {
		super(null, 0, 0, 32, 32, "slot.png");
		layer = 4;
		clickable = false;
	}
	
	public void update() {
		calc();
	}

	@Override
	public void calc() {
		x+=dx;
		y+=dy;
		duration--;
		if(duration == 0){
			killMe = true;
			dispose();
		}

	}
	
	public void dead(){}
	
	public void set(World world, float x, float y, int duration, float dx, float dy){
		this.world = world;
		
		this.x = x;
		this.y = y;
		
		this.duration = duration;
		
		this.dx = dx;
		this.dy = dy;
		
		killMe = false;
		
	}
	
	public abstract void dispose();

}
