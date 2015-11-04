package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.Camera;
import com.dungeon.game.Mouse;
import com.dungeon.game.entity.*;

public class World {
	public Mouse mouse;
	public Camera cam;
	
	public ArrayList<Dungeon> dungeons;
	
	public Dungeon curDungeon;
	public Floor curFloor;
	
	public ArrayList<Entity> entities;
	
	public Entity player;
	
	public World() {
		dungeons = new ArrayList<Dungeon>();
		
		dungeons.add(new Dungeon());
		
		curDungeon = dungeons.get(0);
		curFloor = curDungeon.floors.get(0);
		
		mouse = new Mouse();
		cam = new Camera();
		
		entities = new ArrayList<Entity>();
		
		player = new Player(100, 50);
		
		entities.add(player);
	}
	
	public void update() {
		for(Entity ent: entities) {
			ent.update(this);
		}
		
		mouse.update();
		cam.update(player.x, player.y, mouse.x, mouse.y, 1f);
	}
	
	public void draw(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.cam.combined);
		
		batch.begin();
		curFloor.draw(batch, cam.x, cam.y);
		
		for(Entity ent: entities) {
			ent.draw(batch);
		}
		batch.end();
	}
}
