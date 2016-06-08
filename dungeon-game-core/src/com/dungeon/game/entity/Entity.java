package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

import box2dLight.Light;

public abstract class Entity {
	public float x;
	public float y;
	
	public float origin_x;
	public float origin_y;
	
	public float angle;
	
	public Polygon hitbox;
	
	public int d_width;
	public int d_height;
	
	public int d_offx;
	public int d_offy;
	
	public boolean solid;
	
	public boolean rotate;
	
	public boolean flipX;
	public boolean flipY;
	
	public boolean killMe;
	
	public String name;
	
	public Texture sprite;
	
	public Texture[] textures;
	
	public Light light;
	
	public World world;
	
	public Entity(World world, float x, float y, int width, int height, String filename) {
		this.x = x;
		this.y = y;
		
		this.world = world;
		
		textures = Spritesheet.getSprites(filename, width, height);
		changeSprite(0);
		
		d_width = width;
		d_height = height;
		
		this.killMe = false;
	}
	
	public void update() {
		calc();
		post();
		calcLight();
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x-origin_x+d_offx,/*y*/ y-origin_y+d_offy,/*originX*/origin_x,/*originY*/origin_y,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),flipX,flipY);
	}
	
	public float[] getDrawCenter(){
//		float temp_angle = (float) (Math.atan(origin_y/origin_x)*180/Math.PI+angle);
		float temp_angle = 45+angle;
		float temp_len = (float) Math.sqrt(origin_x*origin_x+origin_y*origin_y);
		float temp_x = (float) (Math.cos(temp_angle/180*Math.PI)*temp_len);
		float temp_y = (float) (Math.sin(temp_angle/180*Math.PI)*temp_len);
		return new float[]{temp_x,temp_y};
	}
	
	public Polygon getHitbox() {
		Polygon temp_hitbox = new Polygon(hitbox.getVertices());
		
		temp_hitbox.setOrigin(origin_x, origin_y);
		temp_hitbox.translate(-origin_x, -origin_y);
		if(rotate)temp_hitbox.rotate(angle);
		temp_hitbox.translate(x, y);
		temp_hitbox.dirty();
		
		
		return new Polygon(temp_hitbox.getTransformedVertices());
	}
	
	public Rectangle getBoundingBox() {
		return getHitbox().getBoundingRectangle();
	}
	
	protected void changeSprite(int index){
		sprite = textures[index];
	}
	
	public void calcLight(){
		if(light!=null){
			System.out.println("light is");
			light.setPosition(new Vector2(x,y));
		}
	}
	
	public void hovered() {} //optional method called when the mouse hovers over an entity

	public void dead(){};
	
	public abstract void calc(); //called at the beginning of an update cycle
	
	public abstract void post(); //called at the end of an update cycle
	
	
}