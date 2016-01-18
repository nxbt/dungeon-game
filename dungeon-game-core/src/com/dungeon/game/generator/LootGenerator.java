package com.dungeon.game.generator;

import com.dungeon.game.entity.Chest;
import com.dungeon.game.item.Arrow;
import com.dungeon.game.item.Bow;
import com.dungeon.game.item.Sword;
import com.dungeon.game.world.Tile;

public class LootGenerator {
	public static Chest getChest(int level, int x, int y){

		Chest chest = new Chest(x*Tile.TS+Tile.TS/2,y*Tile.TS+Tile.TS/2);
		if(Math.random()>0.5)chest.inv.addItem(new Sword((int) (7 + Math.random()*6),10,10));
		else {
			chest.inv.addItem(new Bow((int) (7 + Math.random()*6),10,10));
			Arrow arrow = new Arrow();
			arrow.stack = (int) (Math.random()*12);
			chest.inv.addItem(arrow);
		}
		return chest;
	}
}
