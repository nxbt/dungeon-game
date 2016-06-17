package com.dungeon.game.entity.weapon;

import com.dungeon.game.entity.Dynamic;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.Hand;
import com.dungeon.game.item.weapon.Weapon;
import com.dungeon.game.world.World;

public abstract class HandheldGraphic extends Dynamic {
	
	public Hand item;
	
	public HandheldGraphic(World world, Hand item) {
		super(world, 0, 0, Item.SIZE, Item.SIZE, "slot.png"); // x and y don't matter, they are set every frame
		sprite = item.sprite;
		
		name = "Graphic";
		this.item = item;
		rotate = true;
	}

}
