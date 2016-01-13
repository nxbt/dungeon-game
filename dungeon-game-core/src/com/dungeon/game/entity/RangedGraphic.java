package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class RangedGraphic extends WeaponGraphic {
	
	private boolean toFire;
	
	private float power;
	
	public RangedGraphic(Weapon weapon, float originX, float originY){
		super(weapon);
		this.origin_x = originX;
		this.origin_x = originY;
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void calc(World world) {
		if(toFire) {
			toFire = false;
			Polygon projectileHitBox = new Polygon(new float[]{Item.SIZE*0.1f,Item.SIZE*0.8f,Item.SIZE*0.2f,Item.SIZE*0.9f,0,Item.SIZE*1.1f,Item.SIZE*0.1f,Item.SIZE});
			world.entities.add(new ArrowGraphic((int)x,(int)y,angle,power, projectileHitBox, 2, 30));
		}
	}

	public void fire(float power) {
		toFire = true;
		this.power = power;
	}
}
