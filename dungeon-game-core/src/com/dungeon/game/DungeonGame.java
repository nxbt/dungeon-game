package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class DungeonGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	World world;
	
	private long lastDraw, lastUpdate;
	
	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720);
		
		batch = new SpriteBatch();
		
		world = new World(true);
		
		lastDraw = lastUpdate = System.nanoTime();
	}

	@Override
	public void render () {
//		long s = System.nanoTime();
		long elapseTime = - lastUpdate + (lastUpdate = System.nanoTime());
		world.update(elapseTime);
		if(System.nanoTime() - lastDraw > 16666666){
			world.draw(batch);
			lastDraw = System.nanoTime();
		}
//		System.out.println("Frame in: " + (float)(System.nanoTime() - s)/16000000f + " frames");
	}
	
	@Override
	public void resize(int width, int height) {
	    world.cam.view.update(width, height);
	}
}
