package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.item.Ammo;
import com.dungeon.game.item.Ranged;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class WeaponProjectile extends Projectile {
	
	public Ranged weapon;
	
	public Ammo ammo;
	
	public WeaponProjectile(World world, Ranged weapon, Ammo ammo, float x, float y, float angle, float power, Polygon hitbox, float originX, float originY, int range) {
		super(world, x, y, angle, power, hitbox, originX, originY, range);

		this.weapon = weapon;
		this.ammo = ammo;
		
		sprite = ammo.sprite;
		
		d_width = sprite.getWidth();
		d_height = sprite.getHeight();
		
		owner = weapon.owner;
	}

	@Override
	protected void hit(Character character) {
		ammo.hit(character, weapon, this);
	}
	
	@Override
	public void post() {}
	
	public void stop() {
		Slot tempSlot = new Slot(world, new int[]{0,0,0},null);
		tempSlot.item = ammo;
		
		float xDiff = origin_x-16;
		float yDiff = origin_y-16;
		
		float len = (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		
		float angle = (float) (Math.atan2(yDiff, xDiff)*180/Math.PI) + this.angle;
		
		Drop drop = new Drop(world, (int)(x-Math.cos(angle/180*Math.PI)*len), (int)(y-Math.sin(angle/180*Math.PI)*len), tempSlot);
		drop.angle = this.angle;
		world.entities.add(drop);
	}
}
