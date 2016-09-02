package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class DungeonGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	World world;
	
	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720);
		
		batch = new SpriteBatch();
		
		world = new World(true);
	}

	@Override
	public void render () {
		long s = System.nanoTime();
		world.update();
		world.draw(batch);
		System.out.println("Frame in: " + (float)(System.nanoTime() - s)/16000000f + " frames");
	}
	
	@Override
	public void resize(int width, int height) {
	    world.cam.view.update(width, height);
	}
}
