package com.dungeon.game.entity;

import com.dungeon.game.item.Item;
import com.dungeon.game.item.Weapon;
import com.dungeon.game.world.World;

public abstract class WeaponGraphic extends Dynamic {
	
	public Weapon weapon;
	
	public WeaponGraphic(World world, Weapon weapon) {
		super(world, 0, 0); // x and y don't matter, they are set every frame
		sprite = weapon.sprite;
		this.d_width = Item.SIZE;
		this.d_height = Item.SIZE;
		name = "Graphic";
		this.weapon = weapon;
		rotate = true;
	}

}
