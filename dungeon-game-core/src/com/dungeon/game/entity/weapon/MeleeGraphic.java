package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.equipable.weapon.Melee;
import com.dungeon.game.item.equipable.weapon.Weapon;
import com.dungeon.game.world.World;

public class MeleeGraphic extends HandheldGraphic {
	
	public MeleeGraphic(World world, Weapon weapon, Polygon hitbox, float originX, float originY){
		super(world, weapon, hitbox, originX, originY);
		
	}

	@Override
	public void calc() {
		float distance = Integer.MAX_VALUE;
		Dynamic target = null;
		if(((Melee) item).inAttack()) {
			for(Entity e: world.entities){
				if(!((Melee)item).hasHit&&!e.equals(item.owner) && e instanceof Character && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
					if(Math.sqrt((x-e.x)*(x-e.x)+(y-e.y)*(y-e.y))<distance){
						target = (Dynamic) e;
						distance = (float) Math.sqrt((x-e.x)*(x-e.x)+(y-e.y)*(y-e.y));
					}
				}
			}
		}
		if(target!=null) ((Melee) item).hit((Character) target);
		super.calc();
	}
	
	public void post() {}
}
