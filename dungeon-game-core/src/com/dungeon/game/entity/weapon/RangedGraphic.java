package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.equipable.weapon.Ranged;
import com.dungeon.game.item.equipable.weapon.Weapon;
import com.dungeon.game.world.World;

public class RangedGraphic extends HandheldGraphic {
	
	private boolean toFire;
	
	private float power;
	
	public RangedGraphic(World world, Weapon weapon, Polygon hitbox, float originX, float originY){
		super(world, weapon, hitbox, originX, originY);
	}

	@Override
	public void calc() {
		if(toFire) {
			toFire = false;
			world.entities.add(((Ranged)item).ammoType.getProjectile((Ranged) item, x, y, angle, power));
		}
		super.calc();
	}

	public void fire(float power) {
		toFire = true;
		this.power = power;
	}

	@Override
	public void post() {
		super.post();
	}
}
