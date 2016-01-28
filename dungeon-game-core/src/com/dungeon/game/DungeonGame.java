package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.*;

public class DungeonGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	World world;
	
	@Override
	public void create() {
		Gdx.graphics.setDisplayMode(1280, 720, false);
		
		batch = new SpriteBatch();
		
		world = new World();
	}

	@Override
	public void render () {
		world.update();
		world.draw(batch);
	}
	
	@Override
	public void resize(int width, int height)
	{
	    world.cam.view.update(width, height);
	}
}
