package com.dungeon.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Static extends Entity {
	public Static(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
	}
	
	public void getBody(com.badlogic.gdx.physics.box2d.World world){
		// Create our body definition
		BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.KinematicBody;
		// Set its world position
		bodyDef.position.set(new Vector2(x/Tile.PPM, y/Tile.PPM));  

		// Create a body from the defintion and add it to the world
		box2dBody = world.createBody(bodyDef);  

		// Create a polygon shape
		PolygonShape shape = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		float[] verts = hitbox.getVertices().clone();
		for(int i = 0; i < verts.length; i++){
			verts[i]/=Tile.PPM;
		}
		shape.set(verts);
		
		// Create a fixture from our polygon shape and add it to our ground body  
		Fixture f = box2dBody.createFixture(shape, 0.0f);
		Filter filter = new Filter();
		if(solid){
			filter.maskBits = 0;
			filter.groupIndex = 1;
			filter.categoryBits = 0;
		}
		else{
			filter.maskBits = 0;
			filter.groupIndex = 0;
			filter.categoryBits = 0;
		}
		f.setFriction(0);
		f.setFilterData(filter);
		// Clean up after ourselves
		shape.dispose();
		
	}
}