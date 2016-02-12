package com.dungeon.game.item;

import com.dungeon.game.entity.WeaponProjectile;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public abstract class Ammo extends Item {

	public float damage;
	
	public Ammo(World world){
		super(world);
		type = DEFAULT;
		stack = 1;
		maxStack = 10;
	}

	public abstract void hit(Character character, Ranged weapon, WeaponProjectile projectile);
}
