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
		batch = new SpriteBatch();
		
		world = new World();
		
		Gdx.graphics.setDisplayMode(640, 360, false);
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