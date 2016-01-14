package com.dungeon.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	
	private static int OFFSET = 0;
	public Projectile(int x, int y, float angle, float power, Polygon hitbox, float originX, float originY) {
		super(x, y);
		dx = (float) Math.cos((angle+135)/180*Math.PI)*power;
		dy = (float) Math.sin((angle+135)/180*Math.PI)*power;
		this.angle = angle;
		
		this.hitbox = hitbox;
		
		this.origin_x = originX;
		this.origin_y = originY;
		
	}

	@Override
	public void init() {
		solid = false;

	}

	@Override
	public void calc(World world) {
		for(Entity e: world.entities){
			if(e.name!="Player"&&e instanceof Dynamic && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
//				killMe=true;
			}
		}

	}
	
	public void phys(World world){
		float vel = (float) Math.sqrt(dx*dx+dy*dy);
		if(vel<fric){
			dx=0;
			dy=0;
		}else if(vel!=0){
			dx -= dx/vel*fric;
			dy -= dy/vel*fric;
		}
		
		Polygon hitboxTile;
		if(col(world,false, new float[]{0,0})==1){
			dx = 0;
			dy = 0;
		}
		
		x+=dx;
		y+=dy;
	}

}
