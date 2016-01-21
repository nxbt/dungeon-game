package com.dungeon.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Melee;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;
import com.dungeon.game.entity.Character;

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
	public void calc(World world) {
		float distance = Integer.MAX_VALUE;
		Dynamic target = null;
		if(((Melee) weapon).inAttack()) {
			for(Entity e: world.entities){
				if(!((Melee)weapon).hasHit&&!e.equals(weapon.owner) && e instanceof Dynamic && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
					if(Math.sqrt((x-e.x)*(x-e.x)+(y-e.y)*(y-e.y))<distance){
						target = (Dynamic) e;
						distance = (float) Math.sqrt((x-e.x)*(x-e.x)+(y-e.y)*(y-e.y));
					}
				}
			}
		}
		if(target!=null)weapon.hit((Character) target,null);
	}
	
	public void post(World world) {}
}
