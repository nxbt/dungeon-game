package com.dungeon.game.generator;

import com.dungeon.game.entity.furniture.Chest;
import com.dungeon.game.item.Gold;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.consumable.LifePotion;
import com.dungeon.game.item.weapon.Bow;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class LootGenerator {
	public static Chest getChest(World world, int level, int x, int y){

		Chest chest = new Chest(world, x*Tile.TS+Tile.TS/2,y*Tile.TS+Tile.TS/2);
		if(Math.random()>0.5)chest.inv.addItem(new Sword(world, (float) (6 + Math.random()*6),10, 10, 10));
		else {
			chest.inv.addItem(new Bow(world, (float) (0.7f + Math.random()*0.6f),10));
			Arrow arrow = new Arrow(world);
			arrow.stack = (int) (Math.random()*12);
			chest.inv.addItem(arrow);
		}

		for(double i = Math.random(); i < 0.5; i+=0.1){
			chest.inv.addItem(new LifePotion(world));
		}
		Gold gold = new Gold(world);
		gold.stack = (int) Math.round(Math.random()*15);
		chest.inv.addItem(gold);
		return chest;
	}
}
