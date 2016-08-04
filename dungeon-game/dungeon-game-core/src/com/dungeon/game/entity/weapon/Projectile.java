package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	public float range;
	
	public Character owner;
	
	public Projectile(World world, float x, float y, float angle, float power, Polygon hitbox, float originX, float originY, int range, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		
		solid = false;
		
		Vector2 acelVec = new Vector2();
		acelVec.x = (float) Math.cos((angle+135)/180*Math.PI)*power;
		acelVec.y = (float) Math.sin((angle+135)/180*Math.PI)*power;
		acel(acelVec,false);
		this.angle = angle;
		this.range = range;
		
		rotate = true;
		
		this.hitbox = hitbox;
		
		this.origin_x = originX;
		this.origin_y = originY;
	}

	@Override
	public void calc() {
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
		float vel = getVel();
		range--;
		if(range<0||vel<=fric){
			stop();
			killMe = true;
		}
		else {
			moveVec.x-=moveVec.x/vel*fric;
			moveVec.y-=moveVec.y/vel*fric;
		}
		
		if((moveVec.x != 0 || moveVec.y != 0) && col(false, new float[]{0,0}) > 0){
			moveVec.x = 0;
			moveVec.y = 0;
		}
		
		x+=moveVec.x;
		y+=moveVec.y;
	}
	
	protected abstract void hit(Character character);
	
	protected abstract void stop();
}
