package com.dungeon.game.entity.particle;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.utilities.Pool;
import com.dungeon.game.world.World;

public class Footprint extends Particle {
	
	private Pool<Footprint> pool;
	
	public static Footprint get(World world, Pool<Footprint> pool, float x, float y, float angle){
		Footprint p = pool.get();
		p.set(world, pool, x, y, angle);
		return p;
	}
	
	public Footprint(Texture sprite) {
		super();
		
		layer = FOOTPRINT;
		
		dWidth = 8;
		dHeight = 4;
		
		originX = 4;
		originY = 2;
		
		this.sprite = sprite;
	}
	
	public void calc() {
		alpha = duration/300f;
		super.calc();
	}
	
	public void set(World world, Pool<Footprint> pool, float x, float y, float angle){
		super.set(world, x, y, 300, 0, 0);
		this.pool = pool;
		this.angle = angle;
	}

	@Override
	public void dispose() {
		pool.dispose(this);
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}

}
