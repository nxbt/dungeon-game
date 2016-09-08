package com.dungeon.game.entity.character.enemy;

import java.util.ArrayList;

import com.dungeon.game.entity.Drop;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Gold;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public abstract class Enemy extends Character {
	public Enemy(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		
		team = Character.ENEMY;
	}
	
	public void dead(){
		super.dead();
		if(equipItems[0]!=null)equipItems[0].unequip();
		if(equipItems[1]!=null)equipItems[1].unequip();
		
		ArrayList<Item> drops = inv.getDrops();
		for(Item item: drops){
			Slot slot = new Slot(world, new int[]{0, 0, 0}, null);
			slot.item = item;
			Drop drop = new Drop(world, x, y, slot);
			world.entities.add(drop);
		}
		
		if(gold > 0) {
			Slot goldSlot = new Slot(world, new int[]{0,0,0}, null);
			
			Item goldItem = new Gold(world);
			goldItem.stack = gold;
			
			goldSlot.item = goldItem;
			
			Drop goldDrop = new Drop(world, x, y, goldSlot);
			
			world.entities.add(goldDrop);
		}
	}
}
