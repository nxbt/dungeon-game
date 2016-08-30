package com.dungeon.game.entity.particle;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public class Poof extends Particle {
	
	private static final com.dungeon.game.textures.entity.particle.Poof[] poofs = new com.dungeon.game.textures.entity.particle.Poof[]{
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof(),
			new com.dungeon.game.textures.entity.particle.Poof()
	};

	public Poof(World world, float x, float y) {
		super(world, x, y, 60, (float)(5f - Math.random()*10f), (float) (5f - Math.random() * 10f));
		
		dWidth = 4;
		dHeight = 4;
		
		originX = 2;
		originY = 2;
		
		sprite = poofs[(int) (Math.random()*poofs.length)].texture;
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

}
