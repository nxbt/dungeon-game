package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public abstract class Entity {
	public float x;
	public float y;
	
//	public float width;
//	public float height;
	
	public float origin_x;
	public float origin_y;
	
	public Polygon hitbox;
	
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
		batch.draw(/*Texture*/ sprite,/*x*/ x+sprite.getWidth()/2+d_offx,/*y*/ y+sprite.getHeight()/2+d_offy,/*originX*/origin_x,/*originY*/origin_y,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
	}
	
	public abstract void init();
	
	public abstract void calc(World world);
	
	public void hovered(World world){};
	
	public Polygon getHitbox() {
		Polygon temp_hitbox = new Polygon(hitbox.getVertices());
		
		temp_hitbox.setOrigin(origin_x, origin_y);
		temp_hitbox.rotate(angle);
		temp_hitbox.translate(x, y);
		
		return temp_hitbox;
	}
	
	public Rectangle getBoundingBox() {
		return getHitbox().getBoundingRectangle();
	}
}
