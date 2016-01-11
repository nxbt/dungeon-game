package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.item.Arrow;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public class RangedGraphic extends WeaponGraphic {
	
	private boolean toFire;
	
	public RangedGraphic(Weapon weapon){
		super(weapon);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calc(World world) {
		// TODO Auto-generated method stub
		if(toFire) {
			System.out.println("PEW PEW");
			toFire = false;
			world.entities.add(new ArrowGraphic((int)x,(int)y,angle,100));
		}
	}

	public void fire() {
		toFire = true;
	}
}
