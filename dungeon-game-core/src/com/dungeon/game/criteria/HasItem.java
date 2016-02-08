package com.dungeon.game.criteria;

import com.dungeon.game.world.World;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.item.Item;

public class HasItem extends Criteria{
	private Item item;
	private Character character;
	boolean isTrue;
	
	public HasItem(World world, Item item, Character character, boolean isTrue){
		super(world);
		this.item = item;
		this.character = character;
		this.isTrue = isTrue;
	}
  
	public boolean metCriteria(){
		if(isTrue)return character.inv.contains(item) != null;
		else return !(character.inv.contains(item) != null);
	}
}