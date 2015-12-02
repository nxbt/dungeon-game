package com.dungeon.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.dungeon.game.Camera;
import com.dungeon.game.entity.*;
import com.dungeon.game.item.Slot;

public class World {
	public SpriteBatch hudBatch;
	
	public Camera cam;
	public Camera hudCam;
	
	public ArrayList<Dungeon> dungeons;
	
	public Dungeon curDungeon;
	public Floor curFloor;
	
	public ArrayList<Entity> entities;
	public ArrayList<Entity> hudEntities;
	
	public Player player;
	public Mouse mouse;
	
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
		
		player = new Player(curFloor.tm[0].length/2*Tile.TS, curFloor.tm.length/2*Tile.TS);
		
		mouse = new Mouse(0, 0);
		entities = curFloor.entities;
		System.out.println(entities.size());
		entities.add(0,player);
		entities.add(new Chest((curFloor.tm[0].length/2+1)*Tile.TS, curFloor.tm.length/2*Tile.TS));
	}
	
	public void update() {
		mouse.update(this);
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update(this);
			if(entities.get(i).killMe) {
				entities.remove(i);
				i--;
			}
		}
		
		for(Entity ent: hudEntities) {
			ent.update(this);
		}
		
		cam.update(player.x+player.d_width/2, player.y+player.d_height/2, mouse.x, mouse.y, 1f);
	}
	
	public void draw(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		batch.setProjectionMatrix(cam.cam.combined);
		
		curFloor.draw(batch, this);
		
		for(int i = entities.size()-1; i >= 0; i--) {
			entities.get(i).draw(batch);
		}
		
		batch.setProjectionMatrix(hudCam.cam.combined);
		
		for(Entity ent: hudEntities) {
			ent.draw(batch);
		}
		
		mouse.draw(batch);
		
		batch.end();
	}
}
