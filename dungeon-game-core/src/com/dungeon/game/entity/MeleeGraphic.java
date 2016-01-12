package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Melee;
import com.dungeon.game.item.Ranged;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class MeleeGraphic extends WeaponGraphic {
	
	private Polygon hitbox;
	
	
	public MeleeGraphic(Weapon weapon, Polygon hitbox, float originX, float originY){
		super(weapon);
		
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
		if(((Melee) weapon).inAttack()) {
			Polygon temp_hitbox = new Polygon(hitbox.getVertices());
			
			temp_hitbox.setOrigin(width*originX, height*originY);
			temp_hitbox.rotate(angle);
			temp_hitbox.translate(x, y);
			
			Polygon hitBoxEntity;
			
			for(Entity e: world.entities){
				hitBoxEntity = new Polygon(new float[]{e.x,e.y,e.x+e.width,e.y,e.x+e.width,e.y+e.height,e.x,e.y+e.height});
				
				if(!e.equals(world.player) && e instanceof Dynamic && Intersector.overlapConvexPolygons(temp_hitbox, hitBoxEntity)){
					((Melee) weapon).hit((Dynamic) e);
				}
			}
		}
	}
}
