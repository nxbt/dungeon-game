package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.equipable.weapon.Ranged;
import com.dungeon.game.item.equipable.weapon.Weapon;
import com.dungeon.game.world.Tile;
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
			WeaponProjectile w = new WeaponProjectile(world, (Ranged) item, new Arrow(world), x, y, angle, power, projectileHitBox, 2, 30, 35);
			w.fric = 0.03f;
			w.getBody(world.curFloor.box2dWorld);
			w.bodyMade = true;
			Vector2 acelVec = new Vector2();
			
			acelVec.x = (float) Math.cos((angle+135)/180*Math.PI)*power/Tile.PPM;
			acelVec.y = (float) Math.sin((angle+135)/180*Math.PI)*power/Tile.PPM;
			w.body.setLinearVelocity(acelVec);
			w.prevAngle = acelVec.angle();
			
			world.entities.add(w);
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
