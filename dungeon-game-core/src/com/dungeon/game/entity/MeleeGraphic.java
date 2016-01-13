package com.dungeon.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Melee;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class MeleeGraphic extends WeaponGraphic {
	
	public MeleeGraphic(Weapon weapon, Polygon hitbox, float originX, float originY){
		super(weapon);
		
		this.origin_x = originX;
		this.origin_x = originY;
		
		this.hitbox = hitbox;
		
		this.weapon = weapon;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void calc(World world) {
		if(((Melee) weapon).inAttack()) {
			for(Entity e: world.entities){
				if(!e.equals(world.player) && e instanceof Dynamic && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
					((Melee) weapon).hit((Dynamic) e);
				}
			}
		}
	}
}
