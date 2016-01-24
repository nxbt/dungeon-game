package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class MediumGraphic extends WeaponGraphic {

	public MediumGraphic(World world, Weapon weapon, float originX, float originY){
		super(world, weapon);
		this.origin_x = originX;
		this.origin_y = originY;

		hitbox = new Polygon(new float[]{0,0,0,0,0,0});
	}

	@Override
	public void init() {
		
	}

	@Override
	public void calc() {

	}

	@Override
	public void post() {

	}

}
