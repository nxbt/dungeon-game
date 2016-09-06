package com.dungeon.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
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
		body = world.createBody(bodyDef);  
		
		super.getBody(world);
	}
}