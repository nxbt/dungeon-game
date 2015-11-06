package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.Camera;
import com.dungeon.game.entity.*;

public class World {
	public SpriteBatch hudBatch;
	
	public Camera cam;
	public Camera hudCam;
	
	public ArrayList<Dungeon> dungeons;
	
	public Dungeon curDungeon;
	public Floor curFloor;
	
	public ArrayList<Entity> entities;
	public ArrayList<Entity> hudEntities;
	
	public Entity player;
	public Entity mouse;
	
	public World() {
		hudBatch = new SpriteBatch();
		
		dungeons = new ArrayList<Dungeon>();
		
		dungeons.add(new Dungeon());
		
		curDungeon = dungeons.get(0);
		curFloor = curDungeon.floors.get(0);
		
		cam = new Camera();
		hudCam = new Camera();
		
		entities = new ArrayList<Entity>();
		hudEntities = new ArrayList<Entity>();
		
		player = new Player(100, 50);
		
		mouse = new Mouse(0, 0);
		
		entities.add(player);
		
		hudEntities.add(mouse);
	}
	
	public void update() {
		for(Entity ent: entities) {
			ent.update(this);
		}
		
		for(Entity ent: hudEntities) {
			ent.update(this);
		}
		
		cam.update(player.x, player.y, mouse.x, mouse.y, 1f);
	}
	
	public void draw(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.cam.combined);
		
		batch.begin();
		curFloor.draw(batch, this);
		
		for(Entity ent: entities) {
			ent.draw(batch);
		}
		
		batch.setProjectionMatrix(hudCam.cam.combined);
		
		for(Entity ent: hudEntities) {
			ent.draw(batch);
		}
		
		batch.end();
	}
}
