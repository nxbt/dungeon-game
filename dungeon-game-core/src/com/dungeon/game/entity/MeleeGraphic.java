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
		this.origin_y = originY;
		
		this.hitbox = hitbox;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void calc(World world) { //TODO: make weapons prioritize closest enemies
		if(((Melee) weapon).inAttack()) {
			for(Entity e: world.entities){
				if(!((Melee)weapon).hasHit&&!e.equals(weapon.owner) && e instanceof Dynamic && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
					weapon.hit((Dynamic) e,null);
				}
			}
		}
	}
	
	public void post(World world) {}
}
