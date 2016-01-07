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
	
	public float angle;
	
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
		batch.draw(/*Texture*/ sprite,/*x*/ x+d_offx,/*y*/ y+d_offy,/*originX*/d_width/2,/*originY*/d_height/2,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
	}
	
	public abstract void init();
	
	public abstract void calc(World world);
	
	public void hovered(World world){};
}
