package com.dungeon.game.criteria;

import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.Item;

public class HasItem extends Criteria{
	private Item item;
	private Character character;
	
	public HasItem(World world, Item item, Character character){
		super(world);
		this.item = item;
		this.character = character;
	}
  
	public boolean metCriteria(){
		return character.inv.contains(item) != null;
	}
}
