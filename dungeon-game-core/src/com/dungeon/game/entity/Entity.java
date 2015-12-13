package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public abstract class Entity {
	public float x;
	public float y;
	
	public float width;
	public float height;
	
	public boolean killMe;
	
	public int d_width;
	public int d_height;
	
	public int d_offx;
	public int d_offy;
	
	boolean solid;
	
	public String name;
	
	public Texture sprite;
	
	public Light light;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.killMe = false;
		
		this.init();
	}
	
	public void update(World world) {
		calc(world);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(sprite, x+d_offx, y+d_offy, d_width, d_height);
	}
	
	public abstract void init();
	
	public abstract void calc(World world);
	
	public void hovered(World world){};
}
