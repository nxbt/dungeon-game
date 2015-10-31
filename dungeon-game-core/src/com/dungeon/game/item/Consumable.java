package com.dungeon.game.item;

public abstract class Consumable extends Item {

	public Consumable(String name, String desc, int maxStack) {
		super(name, desc, maxStack);
	}
	
	public void use() {
		
	}
}
