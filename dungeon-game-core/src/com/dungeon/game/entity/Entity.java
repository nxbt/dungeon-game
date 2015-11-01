package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
	public float x;
	public float y;
	
	public float width;
	public float height;
	
	boolean solid;
	
	public String name;
	
	public Texture sprite;
	
	public Entity(String name, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.name = name;
		
		this.init();
	}
	
	public void update() {
		calc();
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(sprite, x, y, width, height);
	}
	
	public abstract void calc();
	
	public abstract void init();
}
