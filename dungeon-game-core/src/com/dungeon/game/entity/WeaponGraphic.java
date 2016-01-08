package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Meele;
import com.dungeon.game.item.Ranged;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class WeaponGraphic extends Static {
	public float d_originX;
	public float d_originY;
	
	private float originX;
	private float originY;
	
	private Polygon hitbox;
	
	private Weapon weapon;
	
	public WeaponGraphic(Texture texture,int width, int height, Weapon weapon, Polygon hitbox, float originX, float originY){
		super(height, height);
		
		this.sprite = texture;
		
		this.width = width;
		this.height = height;
		
		this.d_width = sprite.getWidth();
		this.d_height = sprite.getHeight();
		
		this.originX = originX;
		this.originY = originY;
		
		this.hitbox = hitbox;
		
		this.weapon = weapon;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void calc(World world) {
		if(!(weapon instanceof Ranged) && ((Meele) weapon).inAttack()) {
			Polygon temp_hitbox = new Polygon(hitbox.getVertices());
			
			temp_hitbox.setOrigin(width*originX, height*originY);
			temp_hitbox.rotate(angle);
			temp_hitbox.translate(x, y);
			
			Polygon hitBoxEntity;
			
			for(Entity e: world.entities){
				hitBoxEntity = new Polygon(new float[]{e.x,e.y,e.x+e.width,e.y,e.x+e.width,e.y+e.height,e.x,e.y+e.height});
				
				if(!e.equals(world.player) && e instanceof Dynamic && Intersector.overlapConvexPolygons(temp_hitbox, hitBoxEntity)){
					if(((Dynamic) e).damage(weapon.damage) != 0) ((Meele) weapon).hit((Dynamic) e);
					
				}
			}
		}
	}
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x+d_offx,/*y*/ y+d_offy,/*originX*/d_originX,/*originY*/d_originY,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
	}
}
