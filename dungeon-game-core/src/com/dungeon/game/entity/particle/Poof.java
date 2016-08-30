package com.dungeon.game.entity.particle;

import com.dungeon.game.utilities.Pool;
import com.dungeon.game.world.World;

public class Poof extends Particle {
	
	public static final Pool<Poof> pool = new Pool<Poof>(500){

		@Override
		public Poof getNew() {
			return new Poof();
		}
		
	};
	
	public static void init(){
		Poof p = pool.get();
		p.dispose();
	}
	
	public static Poof get(World world, float x, float y){
		Poof p = pool.get();
		p.set(world, x, y);
		return p;
	}

	public Poof() {
		super();
		
		dWidth = 4;
		dHeight = 4;
		
		originX = 2;
		originY = 2;
		
		sprite = new com.dungeon.game.textures.entity.particle.Poof().texture;
	}
	
	public void calc() {
		dx*=0.8f;
		dy*=0.8f;
		if(Math.sqrt(dx*dx+dy*dy) < 0.4f){
			killMe = true;
			dispose();
		}
		super.calc();
	}

	@Override
	public void post() {}
	
	public void set(World world, float x, float y){
		float angle = (float) (Math.random()*Math.PI*2);
		float vel = (float) (Math.random()*5 + 3);
		super.set(world, x, y, 60, (float)Math.cos(angle)*vel, (float)Math.sin(angle)*vel);
	}

	@Override
	public void dispose() {
		pool.dispose(this);
	}
	

}
