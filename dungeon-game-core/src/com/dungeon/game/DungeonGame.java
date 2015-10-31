package com.dungeon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.*;
import com.dungeon.game.tilemap.Dungeon;

public class DungeonGame extends ApplicationAdapter {
	
	Camera cam;
	SpriteBatch batch;
	Texture img;
	Entity player;
	CursorCtrl cursor;
	
	@Override
	public void create() {
		cursor = new CursorCtrl();
		cam = new Camera();
		batch = new SpriteBatch();
		player = new Player(100,50);
		
		Gdx.graphics.setDisplayMode(720, 480, false);
		
		Dungeon dungeon = new Dungeon();
	}

	@Override
	public void render () {
		cursor.update();
		
		player.update();
		cam.update(player.x, player.y, 1f);
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.getCam().combined);
		
		batch.begin();
		batch.draw(player.sprite, player.x, player.y, 100, 100);
		batch.end();
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