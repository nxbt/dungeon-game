package com.dungeon.game.entity;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.dungeon.game.light.Light;
import com.dungeon.game.utilities.Spritesheet;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Entity {

	protected static final int FLOOR = 0;
	protected static final int FOOTPRINT = 1;
	protected static final int PERSON = 2;
	protected static final int HANDHELD = 3;
	protected static final int ROOF = 4;
	protected static final int PARTICLE = 5;
	
	public float x;
	public float y;
	
	public float originX;
	public float originY;
	
	public float angle;
	
	public Polygon hitbox;
	
	public Polygon visHitbox;
	
	public int dWidth;
	public int dHeight;
	
	public int dOffX;
	public int dOffY;
	
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
	
	public int layer;
	
	protected boolean clickable;
	
	public Body body;
	
	public Entity(World world, float x, float y, int width, int height, String filename) {
		this.x = x;
		this.y = y;
		
		this.world = world;
		
		textures = Spritesheet.getSprites(filename, width, height);
		changeSprite(0);
		
		dWidth = width;
		dHeight = height;
		
		this.killMe = false;
		
		layer = PERSON; //default layer
		
		clickable = true;
	}
	
	public void update() {
		calc();
		post();
		calcLight();
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x-originX+dOffX,/*y*/ y-originY+dOffY,/*originX*/originX,/*originY*/originY,/*width*/ dWidth,/*height*/ dHeight,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),flipX,flipY);
	}
	
	public float[] getDrawCenter(){
//		float temp_angle = (float) (Math.atan(origin_y/origin_x)*180/Math.PI+angle);
		float temp_angle = 45+angle;
		float temp_len = (float) Math.sqrt(originX*originX+originY*originY);
		float temp_x = (float) (Math.cos(temp_angle/180*Math.PI)*temp_len);
		float temp_y = (float) (Math.sin(temp_angle/180*Math.PI)*temp_len);
		return new float[]{temp_x,temp_y};
	}
	
	public Polygon getHitbox() {
		Polygon temp_hitbox = new Polygon(hitbox.getVertices());
		
		temp_hitbox.setOrigin(originX, originY);
		temp_hitbox.translate(-originX, -originY);
		if(rotate)temp_hitbox.rotate(angle);
		temp_hitbox.translate(x, y);
		temp_hitbox.dirty();
		
		
		return new Polygon(temp_hitbox.getTransformedVertices());
	}
	
	public Polygon getVisbox() {
		if(visHitbox == null)System.out.println(this.getClass().getName());
		Polygon temp_hitbox = new Polygon(visHitbox.getVertices());
		
		temp_hitbox.setOrigin(originX, originY);
		temp_hitbox.translate(-originX, -originY);
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
			if(!light.isOn && world.player.knownEntities.contains(this)) light.load();
			else if(light.isOn && !world.player.knownEntities.contains(this)) light.unload();
			light.update();
		}
	}
	
	public void hovered() {} //optional method called when the mouse hovers over an entity
	
	public boolean isHovered(){
		if(!clickable)return false;
		return Intersector.isPointInPolygon(getHitbox().getVertices(), 0,getHitbox().getVertices().length,world.mouse.x+world.cam.x-world.cam.width/2,world.mouse.y+world.cam.y-world.cam.height/2);
		
	}

	public void dead(){
		if(light!=null)light.light.remove(true);
		sprite.dispose();
	};
	
	protected void genVisBox(){
		if(hitbox != null){
			float[] verts = hitbox.getVertices().clone();
			ArrayList<Float> list = new ArrayList<Float>();
			for(float f: verts)list.add(f);
			Collections.reverse(list);
			for(int i = 0; i < verts.length; i++){
				verts[i] = list.get(i);
			}
			for(int i = 0; i < verts.length; i+=2){
				float temp = verts[i];
				verts[i] = verts[i+1];
				verts[i+1] = temp;
			}
			visHitbox = new Polygon(verts);
		}
	}
	
	public void goToBodyPostion(){
		if(body != null){
			x = body.getPosition().x*Tile.PPM;
			y = body.getPosition().y*Tile.PPM;
			angle = (float) (body.getAngle()*180/Math.PI);
		}
	}
	
	public abstract void calc(); //called at the beginning of an update cycle
	
	public abstract void post(); //called at the end of an update cycle
	
	public void getBody(com.badlogic.gdx.physics.box2d.World world) {
		// Create a polygon shape
		PolygonShape shape = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		float[] verts = hitbox.getVertices().clone();
		for(int i = 0; i < verts.length; i++){
			if(i % 2 == 1)verts[i]-=originY;
			else verts[i]-=originX;
			verts[i]/=Tile.PPM;
		}
		shape.set(verts);
		
		// Create a fixture from our polygon shape and add it to our ground body  
		Fixture f = body.createFixture(shape, 0.0f);
		Filter filter = new Filter();
		filter.categoryBits = 0x0002;
		if(solid){
			filter.maskBits = -1;
		}
		else{
			filter.maskBits = 0;
		}
		f.setFriction(0);
		f.setFilterData(filter);
		// Clean up after ourselves
		body.setLinearDamping(0.1f);
		shape.dispose();
	}
	
	
}