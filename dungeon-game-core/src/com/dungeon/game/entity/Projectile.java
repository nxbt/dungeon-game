package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Melee;
import com.dungeon.game.world.World;

public abstract class Projectile extends Dynamic {
	
	private static int OFFSET = 70;
	
	private Polygon hitbox;
	
	private float originX;
	private float originY;
	
	protected float d_originX;
	protected float d_originY;
	public Projectile(int x, int y, float angle, float power, Polygon hitbox, float originX, float originY) {
		super((int) (x+Math.cos((angle+135)/180*Math.PI)*OFFSET), (int) (y+Math.sin((angle+135)/180*Math.PI)*OFFSET));
		dx = (float) Math.cos((angle+135)/180*Math.PI)*power;
		dy = (float) Math.sin((angle+135)/180*Math.PI)*power;
		this.angle = angle;
		
		this.hitbox = hitbox;
		
		this.originX = originX;
		this.originY = originY;
		
	}

	@Override
	public void init() {
		solid = false;

	}

	@Override
	public void calc(World world) {
		Polygon temp_hitbox = new Polygon(hitbox.getVertices());
		
		temp_hitbox.setOrigin(width*originX, height*originY);
		temp_hitbox.rotate(angle);
		temp_hitbox.translate(x, y);
		
		Polygon hitboxEntity;
		
		for(Entity e: world.entities){
			hitboxEntity = new Polygon(new float[]{e.x,e.y,e.x+e.width,e.y,e.x+e.width,e.y+e.height,e.x,e.y+e.height});
			
			if(e.name!="Player"&&e instanceof Dynamic && Intersector.overlapConvexPolygons(temp_hitbox, hitboxEntity)){
//				killMe=true;
				
			}
		}

	}
	
	public void phys(World world){
		dx -= (float) Math.cos((angle+135)/180*Math.PI)*fric;
		dy -= (float) Math.cos((angle+135)/180*Math.PI)*fric;
		
		Polygon temp_hitbox = new Polygon(hitbox.getVertices());
		
		temp_hitbox.setOrigin(width*originX, height*originY);
		temp_hitbox.rotate(angle);
		temp_hitbox.translate(x, y);
		
		Polygon hitboxTile;
		
		for(int  i = 0; i < world.curFloor.tm.length;i++){
			for(int k = 0; k <world.curFloor.tm[i].length; k++){
				if(i.data = 1){
					hitboxTile = new Polygon(k*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,i*Tile.TS,(k+1)*Tile.TS,(i+1)*Tile.TS,k*Tile.TS,(i+1)*Tile.TS);
					if(Intersecotr.overlapConvexPolygons(hitboxTile,temp_hitbox);{
						dx = 0;
						dy = 0;
					}
				}
			}
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x+d_offx,/*y*/ y+d_offy,/*originX*/d_originX,/*originY*/d_originY,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
	}

}
