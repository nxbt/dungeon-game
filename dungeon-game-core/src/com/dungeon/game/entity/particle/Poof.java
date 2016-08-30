package com.dungeon.game.entity.particle;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Poof extends Particle {

	public Poof(World world, float x, float y) {
		super(world, x, y, 60, (float)(5f - Math.random()*10f), (float) (5f - Math.random() * 10f));
		
		dWidth = 4;
		dHeight = 4;
		
		originX = 2;
		originY = 2;
		
		sprite = new com.dungeon.game.textures.entity.particle.Poof().texture;
		
		hitbox = new Polygon(new float[]{0,0,4,0,4,4,0,4});
		genVisBox();
	}
	
	public void calc() {
		moveVec.x*=0.8f;
		moveVec.y*=0.8f;
		if(moveVec.len() < 0.2f)killMe = true;
		super.calc();
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub
		
	}

}
