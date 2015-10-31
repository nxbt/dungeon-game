package com.dungeon.game.item;

public abstract class Equippable extends Item {

	public int wear;
	public int curWear;
	
	public Equippable(String name, String desc, int wear) {
		super(name, desc, 1);
		
		this.wear  = wear;
		curWear = wear;
	}

}
