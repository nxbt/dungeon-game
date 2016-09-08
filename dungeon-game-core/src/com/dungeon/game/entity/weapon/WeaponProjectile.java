package com.dungeon.game.entity.weapon;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.ammo.Ammo;
import com.dungeon.game.item.equipable.weapon.Ranged;
import com.dungeon.game.world.World;

public class WeaponProjectile extends Projectile {
	
	public Ranged weapon;
	
	public Ammo ammo;
	
	public WeaponProjectile(World world, Ranged weapon, Ammo ammo, float x, float y, float angle, float power, Polygon hitbox, float originX, float originY, int range) {
		super(world, x, y, angle, power, hitbox, originX, originY, range, Item.SIZE, Item.SIZE, "slot.png");

		this.weapon = weapon;
		this.ammo = ammo;
		
		sprite = ammo.sprite;
		
		owner = weapon.owner;
		
		fric = 0.2f;
		dens = 0;
	}

	@Override
	protected void hit(Character character) {
		ammo.hit(character, weapon, this);
	}
	
	@Override
	public void post() {}
	
	public void stop() {
		

		if(Math.random() < ammo.dropChance){
			Slot tempSlot = new Slot(world, new int[]{0,0,0},null);
			tempSlot.item = ammo;
			
			float xDiff = originX-16;
			float yDiff = originY-16;
			
			float len = (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			
			float angle = (float) (Math.atan2(yDiff, xDiff)*180/Math.PI) + this.angle;
			
			Drop drop = new Drop(world, (float)(x-Math.cos(angle/180*Math.PI)*len), (float)(y-Math.sin(angle/180*Math.PI)*len), tempSlot);
			drop.angle = this.angle;
			world.entities.add(drop);
		}
	}
	
	public void dead(){
		if(light!=null)light.light.remove(true);
	}
}
