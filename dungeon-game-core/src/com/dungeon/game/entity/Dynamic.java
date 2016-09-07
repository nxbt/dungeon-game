package com.dungeon.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.dungeon.game.entity.weapon.WeaponProjectile;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

//abstract class for dynamic entities, or entities that move and respond to physics.
public abstract class Dynamic extends Entity {
	public float fric;
	public float dens;
	
	public ArrayList<int[]> collisions;
	
	public Vector2 moveVec;
	
	public Dynamic(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		
		collisions = new ArrayList<int[]>();
		
		moveVec = new Vector2(0,0);
		
		fric = 0.2f;
		dens = 1;
	}

	//entity update function called on every frame before the draw phase.
	@Override
	public void update() {
		norm();
		calc();
		phys();
		post();
		calcLight();
	}
	
	//resets some variables at the start of every update cycles
	public void norm() {}
	
	//calculates velocity and collisions for object
	public void phys() {}
	
	public void acel(Vector2 vector, boolean trim){
		if(body != null)body.applyForceToCenter(vector.x, vector.y, true);
	}
	
	public float getVel(){
		return (float) Math.sqrt(moveVec.x*moveVec.x+moveVec.y*moveVec.y);
	}
	
	public void getBody(com.badlogic.gdx.physics.box2d.World world){
		// Create our body definition
		BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.DynamicBody;
		
		// Set its world position
		bodyDef.position.set(new Vector2(x/Tile.PPM, y/Tile.PPM));  

		// Create a body from the defintion and add it to the world
		body = world.createBody(bodyDef);  
		
		super.getBody(world);
		
		body.setLinearDamping(fric);
		
		body.getFixtureList().get(0).setDensity(dens);
			
		body.resetMassData();
	}
}
