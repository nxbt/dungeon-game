package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {
	public static final int SIZE = 32;
	
	public int stack;
	public int maxStack;
	
	public int type;
	
	public String name;
	public String desc;
	
	public Texture sprite;
	
	public Item() {
		init();
	}
	
	public abstract void init();
}