package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.world.World;

public abstract class Item implements Cloneable {
	public static final int SIZE = 32;
	
	//Type Constants
	protected static final int DEFAULT = 0;
	protected static final int CONSUMEABLE = 1;
	protected static final int WEAPON = 2;
	protected static final int GLOVES = 3;
	protected static final int HELM = 4;
	protected static final int CHESTPLATE = 5;
	protected static final int PANTS = 6;
	protected static final int BOOTS = 7;
	protected static final int AMULET = 8;
	protected static final int RING = 9;
	
	public int stack;
	public int maxStack;
	
	public int type;
	
	public String name;
	public String desc;
	
	public Texture sprite;
	
	public float dropChance;
	
	protected World world;
	
	public Item(World world) {
		this.world = world;
		
		stack = 1;
		
		init();
	}
	
	public Item clone() {
		try {
			return (Item) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public abstract void init();
	
	public String getDesc() {
		return "We were too lazy to write anything about this. Here's the normal description:\n\n" + desc;
	}
}