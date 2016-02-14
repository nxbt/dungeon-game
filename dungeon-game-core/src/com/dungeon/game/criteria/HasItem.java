package com.dungeon.game.criteria;

import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class HasItem extends Criteria{
	private Item item;
	private Character character;
	
	int stackSize;
	
	public HasItem(World world, Item item, Character character){
		super(world);
		
		this.item = item;
		this.character = character;
		stackSize = 1;
	}
  
	public HasItem(World world, Item item, Character character, int stackSize) {
		super(world);
		
		this.item = item;
		this.character = character;
		this.stackSize = stackSize;
	}

	public boolean metCriteria(){
		if(stackSize <= 1) return character.inv.contains(item) != null;
		else return character.inv.contains(item, stackSize) != null;
	}
}
