package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class DungeonGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	World world;
	
	private long lag;
	
	private long t;
	
	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(1280, 720);
		
		batch = new SpriteBatch();
		
		world = new World(true);
		
		t = System.nanoTime();
	}

	@Override
	public void render () {
		lag += System.nanoTime() - t;
		t = System.nanoTime();
		while(lag >= 16666666) {
			world.update();
			lag -= 16666666;
		} 
		
		world.draw(batch);
	}
	
	@Override
	public void resize(int width, int height) {
	    world.cam.view.update(width, height);
	}
}
