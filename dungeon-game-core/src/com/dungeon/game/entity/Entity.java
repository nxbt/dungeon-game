package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity {
	private int x;
	private int y;
	
	private boolean solid;
	
	private String name;
	
	private Texture sprite;
	
	public Entity(){
		this.x = 0;
		this.y = 0;
		
		this.name = "null";
		
		this.solid = false;
		
		this.sprite = new Texture("badlogic.jpg");
	}
	
	public Entity(int x, int y){
		this.x = x;
		this.y = y;
		
		this.name = "null";
		
		this.solid = false;
		
		this.sprite = new Texture("badlogic.jpg");
	}
	
	public Entity(String name, int x, int y) {
		this.x = x;
		this.y = y;
		
		this.name = name;
		
		this.solid = false;
		
		this.sprite = new Texture("badlogic.jpg");
	}
	
	public Entity(String name, int x, int y, boolean solid) {
		this.x = x;
		this.y = y;
		
		this.name = name;
		
		this.solid = solid;
		
		this.sprite = new Texture("badlogic.jpg");
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public Texture getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = new Texture(sprite);
	}
	
	public void update() {
		calc();
	}
	
	public abstract void calc();
}
