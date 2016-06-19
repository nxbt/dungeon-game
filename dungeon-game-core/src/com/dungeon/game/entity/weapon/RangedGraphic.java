package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.weapon.Ranged;
import com.dungeon.game.item.weapon.Weapon;
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
			Polygon projectileHitBox = new Polygon(new float[]{1,28,4,31,0,32});
			world.entities.add(new WeaponProjectile(world, (Ranged) item, new Arrow(world), x, y, angle, power, projectileHitBox, 2, 30, 35));
		}
	}

	public void fire(float power) {
		toFire = true;
		this.power = power;
	}

	@Override
	public void post() {}
}
