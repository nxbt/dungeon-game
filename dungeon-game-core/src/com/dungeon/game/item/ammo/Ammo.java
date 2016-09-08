package com.dungeon.game.item.ammo;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.weapon.WeaponProjectile;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.weapon.Ranged;
import com.dungeon.game.world.World;

public abstract class Ammo extends Item {

	public float damage;
	
	public Ammo(World world, String filename){
		super(world, filename);
		type = DEFAULT;
		stack = 1;
		maxStack = 10;
	}
	
	public abstract WeaponProjectile getProjectile(Item item, float x, float y, float angle, float power);

	public abstract void hit(Character character, Ranged weapon, WeaponProjectile projectile);
}
