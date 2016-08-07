package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
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
		Entity target = null;
		if(((Melee) item).inAttack()) {
			for(Entity e: world.entities){
				if(!((Melee)item).hasHit&& !e.equals(this) && !e.equals(item.owner) && (e instanceof Character || e instanceof MeleeGraphic) && Intersector.overlapConvexPolygons(getHitbox(), e.getHitbox())){
					if(e instanceof MeleeGraphic && ((Melee)((MeleeGraphic) e).item).inAttack()) {
						target = e;
						System.out.println(((Melee) item).hasHit);
						((Melee) item).hasHit = true;
						break;
					}
					else if(Math.sqrt((x-e.x)*(x-e.x)+(y-e.y)*(y-e.y))<distance){
						target = e;
						distance = (float) Math.sqrt((x-e.x)*(x-e.x)+(y-e.y)*(y-e.y));
					}
				}
			}
		}
		if(target != null && target instanceof MeleeGraphic)  ((Melee) item).knockback(((MeleeGraphic) target).item.owner);
		else if(target != null && target instanceof Character) ((Melee) item).hit((Character) target);
		super.calc();
	}
	
	public void post() {}
}
