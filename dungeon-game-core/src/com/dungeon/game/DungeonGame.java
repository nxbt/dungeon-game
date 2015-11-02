package com.dungeon.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.*;
import com.dungeon.game.world.*;

public class DungeonGame extends ApplicationAdapter {
	Mouse mouse;
	Camera cam;
	
	SpriteBatch batch;
	
	Entity player;
	
	World world;
	Dungeon dungeon;
	Floor floor;
	
	ArrayList<Entity> entities;
	
	@Override
	public void create() {
		mouse = new Mouse();
		cam = new Camera();
		
		batch = new SpriteBatch();
		
		player = new Player(100,50);
		
		world = new World();
		dungeon = world.dungeons.get(0);
		floor = dungeon.floors.get(0);
		
		entities = floor.entities;
		
		entities.add(player);
		
		Gdx.graphics.setDisplayMode(640, 360, false);
	}

	@Override
	public void render () {
		for(Entity ent: entities) {
			ent.update(floor);
		}
		
		mouse.update();
		cam.update(player.x, player.y, mouse.x, mouse.y, 1f);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.cam.combined);
		
		batch.begin();
		floor.draw(batch, cam.x, cam.y);
		
		for(Entity ent: entities) {
			ent.draw(batch);
		}
		batch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
	    cam.view.update(width, height);
	}
}

/*
 * Drop
 * Inventory
 * InputHandler
 * NPC
 * Projectile
 */