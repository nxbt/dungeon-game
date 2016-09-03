package com.dungeon.game.entity.particle;

import com.dungeon.game.utilities.Pool;
import com.dungeon.game.world.World;

public class Ember extends Particle {
	

	
	public static final Pool<Ember> pool = new Pool<Ember>(500){

		@Override
		public Ember getNew() {
			return new Ember();
		}
		
	};
	
	public static void init(){
		Ember p = pool.get();
		p.dispose();
	}
	
	public static Ember get(World world, float x, float y, float angle, float vel){
		Ember p = pool.get();
		p.set(world, x, y, angle, vel);
		return p;
	}
	
	public Ember() {
		super();
	
		dWidth = 2;
		dHeight = 2;
	
		originX = 1;
		originY = 1;
	
		sprite = new com.dungeon.game.textures.entity.particle.Ember().texture;
	}
	
	public void calc(){
		dx+=Math.random()*0.2 - 0.1;
		dy+=Math.random()*0.2 - 0.1;
		super.calc();
	}

	@Override
	public void dispose() {
		pool.dispose(this);

	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}
	
	public void set(World world, float x, float y, float angle, float vel){
		super.set(world, x, y, 60,  (float)(Math.cos(angle/180*Math.PI)*vel + Math.random()*1f - 0.5f), (float)(Math.sin(angle/180*Math.PI)*vel + Math.random()*1f - 0.5f));
	}

}
