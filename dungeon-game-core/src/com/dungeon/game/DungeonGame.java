package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.*;
import com.dungeon.game.tilemap.Dungeon;
import com.dungeon.game.tilemap.Floor;
import com.dungeon.game.tilemap.Tile;

public class DungeonGame extends ApplicationAdapter {
	Dungeon dungeon;
	Mouse mouse;
	Camera cam;
	Cursor cursor;
	SpriteBatch batch;
	Texture img;
	Entity player;
	
	@Override
	public void create() {
		mouse = new Mouse();
		cam = new Camera();
		batch = new SpriteBatch();
		player = new Player(100,50);
		dungeon = new Dungeon();
		dungeon.floors.add(new Floor(100,100));
		
		Gdx.graphics.setDisplayMode(1920, 1080, false);
	}

	@Override
	public void render () {
		mouse.update();
		player.update();
		
		cam.update(player.x+50, player.y+50, mouse.x, mouse.y, 1f);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.cam.combined);
		
		batch.begin();
		for(int i = 0; i<dungeon.floors.get(0).tm.length;i++){
			for(int k = 0; k<dungeon.floors.get(0).tm[i].length;k++){
				batch.draw(dungeon.floors.get(0).tm[i][k].texture, i*Tile.TS, k*Tile.TS);
			}
		}
		
		batch.draw(player.sprite, player.x, player.y, 100, 100);
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
 * Item
 * Inventory
 * InputHandler
 * Level
 * Dungeon
 * Tile
 * Enemy
 * NPC
 * Alive (to implement to entities)
 * Projectile
 */