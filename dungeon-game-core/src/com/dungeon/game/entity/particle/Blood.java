package com.dungeon.game.entity.particle;

import com.dungeon.game.utilities.Pool;
import com.dungeon.game.world.World;

public class Blood extends Particle {
	
	private int totalTime;
	
	public static final Pool<Blood> pool = new Pool<Blood>(500){

		@Override
		public Blood getNew() {
			return new Blood();
		}
		
	};
	
	public static void init(){
		Blood p = pool.get();
		p.dispose();
	}
	
	public static Blood get(World world, float x, float y, float angle, float vel){
		Blood p = pool.get();
		p.set(world, x, y, angle, vel);
		return p;
	}
	

	public Blood() {
		super();
		
		dWidth = 4;
		dHeight = 4;
		
		originX = 2;
		originY = 2;
		
		sprite = new com.dungeon.game.textures.entity.particle.Blood().texture;
	}
	
	public void calc() {
		dx*=0.8f;
		dy*=0.8f;
		alpha = duration/(float)(totalTime);
//		if(Math.sqrt(dx*dx+dy*dy) < 0.4f){
//			killMe = true;
//			dispose();
//		}
		super.calc();
	}

	@Override
	public void post() {
	}
	
	public static float getAngle(float angle){
		return (float) ((angle+20f-Math.random()*40)*Math.PI/180f);
	}
	
	public static float getVel(float vel){
		return (float) (vel *(0.7f+Math.random())*1.3f);
	}

	@Override
	public void dispose() {
		pool.dispose(this);
	}
	
	public void set(World world, float x, float y, float angle, float vel){
		totalTime = (int) (50+Math.random()*200);
		super.set(world, x, y, totalTime, (float)(Math.cos(getAngle(angle)) * getVel(vel)), (float) (Math.sin(getAngle(angle))* getVel(vel)));
	}

}
