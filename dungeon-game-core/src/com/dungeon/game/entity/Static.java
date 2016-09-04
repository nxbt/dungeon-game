package com.dungeon.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
		bodyDef.position.set(new Vector2(x, y));  

		// Create a body from the defintion and add it to the world
		box2dBody = world.createBody(bodyDef);  

		// Create a polygon shape
		PolygonShape shape = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		shape.set(hitbox.getVertices());
		
		// Create a fixture from our polygon shape and add it to our ground body  
		Fixture f = box2dBody.createFixture(shape, 0.0f);
		if(!solid){
			Filter filter = new Filter();
			filter.maskBits = 0;
			f.setFilterData(filter);
		}
		// Clean up after ourselves
		shape.dispose();
		
	}
}