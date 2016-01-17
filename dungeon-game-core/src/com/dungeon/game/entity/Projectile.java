package com.dungeon.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	private Weapon weapon;
	
	public float power;
	
	private static int OFFSET = 0;
	
	public float range;
	
	public Dynamic owner;
	
	public Projectile(int x, int y, float angle, float power, Polygon hitbox, float originX, float originY, Weapon weapon) {
		super(x, y);
		dx = (float) Math.cos((angle+135)/180*Math.PI)*power;
		dy = (float) Math.sin((angle+135)/180*Math.PI)*power;
		range = 35;
		this.angle = angle;
		
		rotate = true;
		
		this.owner = weapon.owner;
		
		this.hitbox = hitbox;
		
		this.origin_x = originX;
		this.origin_y = originY;
		this.weapon = weapon;
		this.power = power;
	}

	@Override
	public void init() {
		solid = false;

	}

	@Override
	public void calc(World world) { //TODO: make projectile turn into pickupable items
		for(Entity e: world.entities){
			if(!e.equals(owner)&& e.solid && e instanceof Dynamic && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
				weapon.hit((Dynamic) e,this);
				killMe = true;
			}
		}

	}
	
	public void phys(World world){
		float vel = (float) Math.sqrt(dx*dx+dy*dy);
		range--;
		if(range<0||vel<fric){
			dx = 0;
			dy = 0;
		}else{
			dx-=dx/vel*fric;
			dy-=dy/vel*fric;
		}
		
		Polygon hitboxTile;
		if((dx != 0 || dy != 0) && col(world,false, new float[]{0,0})==1){
			dx = 0;
			dy = 0;
		}
		
		x+=dx;
		y+=dy;
	}

}
