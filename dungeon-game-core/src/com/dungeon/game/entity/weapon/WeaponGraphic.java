package com.dungeon.game.entity.weapon;

import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.world.World;

public abstract class WeaponGraphic extends Dynamic {
	
	public Weapon weapon;
	
	public WeaponGraphic(World world, Weapon weapon) {
		super(world, 0, 0, Item.SIZE, Item.SIZE, "slot.png"); // x and y don't matter, they are set every frame
		sprite = weapon.sprite;
		
		name = "Graphic";
		this.weapon = weapon;
		rotate = true;
	}

}
