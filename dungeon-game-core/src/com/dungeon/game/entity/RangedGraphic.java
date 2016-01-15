package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class RangedGraphic extends WeaponGraphic {
	
	private boolean toFire;
	
	private float power;
	
	public RangedGraphic(Weapon weapon, float originX, float originY){
		super(weapon);
		this.origin_x = originX;
		this.origin_y = originY;

		hitbox = new Polygon(new float[]{0,0,0,0,0,0});
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void calc(World world) {
		if(toFire) {
			toFire = false;
			Polygon projectileHitBox = new Polygon(new float[]{1,28,4,31,0,32});
			world.entities.add(new ArrowGraphic((int)x,(int)y,angle,power, projectileHitBox, 2, 30, weapon));
		}
	}

	public void fire(float power) {
		toFire = true;
		this.power = power;
	}

	@Override
	public void post(World world) {}
}
