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
		
<<<<<<< HEAD
		
		world = new World();
		dungeon = world.dungeons.get(0);
		floor = dungeon.floors.get(0);
		player = new Player(floor.tm[0].length*Tile.TS/2,floor.tm.length*Tile.TS/2);
		System.out.println(player.x);
		
		entities = floor.entities;
=======
		world = new World();
>>>>>>> refs/remotes/origin/master
		
		Gdx.graphics.setDisplayMode(1280, 720, false);
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
