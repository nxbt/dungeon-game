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
		
		entities = new ArrayList<Entity>();
		
		entities.add(player);
		
		world = new World();
		dungeon = world.dungeons.get(0);
		floor = dungeon.floors.get(0);
		
		Gdx.graphics.setDisplayMode(1280, 720, false);
	}

	@Override
	public void render () {
		for(Entity ent: entities) {
			ent.update();
		}
		
		mouse.update();
		cam.update(player.x+50, player.y+50, mouse.x, mouse.y, 1f);
		
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