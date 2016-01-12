package com.dungeon.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	
	private static int OFFSET = 0;
	public Projectile(int x, int y, float angle, float power, Polygon hitbox, float originX, float originY) {
		super((int) (x+Math.cos((angle+135)/180*Math.PI)*OFFSET), (int) (y+Math.sin((angle+135)/180*Math.PI)*OFFSET));
		dx = (float) Math.cos((angle+135)/180*Math.PI)*power;
		dy = (float) Math.sin((angle+135)/180*Math.PI)*power;
		this.angle = angle;
		
		this.hitbox = hitbox;
		
		this.origin_x = originX;
		this.origin_x = originY;
		
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
		
//		Polygon hitboxTile;
//		for(int  i = 0; i < world.curFloor.tm.length;i++){
//			for(int k = 0; k <world.curFloor.tm[i].length; k++){
//				if(world.curFloor.tm[i][k].data == 1){
//					hitboxTile = new Polygon(new float[]{k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS,k*Tile.TS,(i+1)*Tile.TS});
//					if(Intersector.overlapConvexPolygons(hitboxTile,temp_hitbox)){
//						dx = 0;
//						dy = 0;
//					}
//				}
//			}
//		}
		
		x+=dx;
		y+=dy;
	}

}
