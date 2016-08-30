package com.dungeon.game.inventory;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.ShopGraphic;
import com.dungeon.game.world.World;

public class Shop extends Inventory {
	
	public int[] costs;
	
	public Character owner;

	public Shop(World world, int[][] layout, int[] costs, Character owner, float x, float y) {
		super(world, layout, x, y);
		
		this.costs = costs;
		this.owner = owner;
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new ShopSlot(world, layout[i], this, costs[i]);
		}
		this.graphic = new ShopGraphic(world, this, 0, 0);
	}
	
}
