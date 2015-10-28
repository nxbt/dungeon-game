package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity {
	public float x;
	public float y;
	
	boolean solid;
	
	public String name;
	
	public Texture sprite;
	
	public Entity(){
		this.x = 0;
		this.y = 0;
		
		this.name = "null";
		
		this.init();
	}
	
	public Entity(int x, int y){
		this.x = x;
		this.y = y;
		
		this.name = "null";
		
		this.init();
	}
	
	public Entity(String name, int x, int y) {
		this.x = x;
		this.y = y;
		
		this.name = name;
		
		this.init();
	}
	
	public void update() {
		calc();
	}
	
	public abstract void calc();
	
	public abstract void init();
}
