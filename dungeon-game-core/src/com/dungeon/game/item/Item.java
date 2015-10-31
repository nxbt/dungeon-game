package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {
	public static int nextId = 0;
	
	public int id;
	
	public int stack;
	public int maxStack;
	
	public String name;
	public String desc;
	
	public Texture sprite;
	
	public Item(String name, String desc, int maxStack) {
		this.name = name;
		this.desc = desc;
		
		this.id = nextId;
		
		this.stack = 1;
		
		nextId++;
	}
}