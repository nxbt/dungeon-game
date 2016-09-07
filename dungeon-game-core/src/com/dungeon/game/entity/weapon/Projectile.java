package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	
	public float maxRange;
	
	public float range;
	
	public Character owner;
	
	public float prevAngle; //Float so we can do null check
	
	public Projectile(World world, float x, float y, float angle, float power, Polygon hitbox, float originX, float originY, int range, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		layer = 2;
		
		solid = true;
		this.angle = angle;
		this.range = range;
		maxRange = range;
		
		rotate = true;
		
		this.hitbox = hitbox;
		genVisBox();
		
		this.originX = originX;
		this.originY = originY;
		clickable = false;
		
		prevAngle = Integer.MAX_VALUE;
	}

	@Override
	public void calc() {
		if(range == maxRange)return;
		for(int i = 0; i < world.entities.size();i++){
			Entity e = world.entities.get(i);
			if(!e.equals(owner)&& e.solid && e instanceof Character && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
				hit((Character)e);
				killMe = true;
				break;
			}
		}

	}
	
	public void phys(){
		range--;
		if(range<0||(range + 1 != maxRange && body.getLinearVelocity().len()<=0.01) || (prevAngle != Integer.MAX_VALUE && Math.abs(prevAngle - body.getLinearVelocity().angle()) > 0.1)){
			stop();
			killMe = true;
		}
		if((range + 1 != maxRange))prevAngle = body.getLinearVelocity().angle();
	}
	
	protected abstract void hit(Character character);
	
	protected abstract void stop();
	
	public void getBody(com.badlogic.gdx.physics.box2d.World world){
		super.getBody(world);

		Fixture f = body.getFixtureList().get(0);
		Filter filter = f.getFilterData();
		filter.maskBits = 0x0001;
		f.setFilterData(filter);
		body.setLinearDamping(0.03f);
	}
}
