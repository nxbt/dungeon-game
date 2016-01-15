package com.dungeon.game.entity;

import com.dungeon.game.item.Item;
import com.dungeon.game.item.Weapon;

public abstract class WeaponGraphic extends Static {
	
	protected Weapon weapon;
	
	public WeaponGraphic(Weapon weapon) {
		super(0, 0); // x and y don't matter, they are set every frame
		sprite = weapon.sprite;
		this.d_width = Item.SIZE;
		this.d_height = Item.SIZE;
		name = "Graphic";
	}

}
