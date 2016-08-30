package com.dungeon.game.entity.particle;

import java.util.Random;

import com.dungeon.game.world.World;

public class Blood extends Particle {
	
	private static final com.dungeon.game.textures.entity.particle.Blood[] bloods = new com.dungeon.game.textures.entity.particle.Blood[]{
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood(),
			new com.dungeon.game.textures.entity.particle.Blood()
	};

	public Blood(World world, float x, float y, float angle, float vel) {
		super(world, x, y, 60, (float)(Math.cos(getAngle(angle)) * getVel(vel)), (float) (Math.sin(getAngle(angle))* getVel(vel)));
		
		dWidth = 4;
		dHeight = 4;
		
		originX = 2;
		originY = 2;
		
		sprite = bloods[(int) (Math.random()*bloods.length)].texture;
	}
	
	public void calc() {
		dx*=0.8f;
		dy*=0.8f;
		if(Math.abs(dx) + Math.abs(dy) < 0.4f)killMe = true;
		super.calc();
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub
	}
	
	public static float getAngle(float angle){
		return (float) ((angle+45f-Math.random()*90)*Math.PI/180f);
	}
	
	public static float getVel(float vel){
		return (float) (vel *(0.7f+Math.random())*1.3f);
	}

}
