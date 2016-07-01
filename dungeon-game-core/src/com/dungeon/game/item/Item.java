package com.dungeon.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.spritesheet.Spritesheet;
import com.dungeon.game.world.World;

public abstract class Item implements Cloneable {
	public static final int SIZE = 32;
	
	//Type Constants
	public static final int DEFAULT = 0;
	public static final int CONSUM = 1;
	public static final int HAND = 2;
	public static final int GLOVES = 3;
	public static final int HELM = 4;
	public static final int CHEST = 5;
	public static final int PANTS = 6;
	public static final int BOOTS = 7;
	public static final int AMULET = 8;
	public static final int RING = 9;
	
	public int stack;
	public int maxStack;
	
	public int type;
	
	public String name;
	public String desc;
	
	public Texture sprite;
	
	protected Texture[] textures;
	
	public float dropChance;
	
	protected World world;
	
	public Item(World world, String filename) {
		this.world = world;
		
		textures = Spritesheet.getSprites(filename, SIZE, SIZE);
		changeSprite(0);
		
		stack = 1;
	}
	
	public Item clone() {
		try {
			return (Item) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getDesc() {
		return "We were too lazy to write anything about this. Here's the normal description:\n\n" + desc;
	}
	
	protected void changeSprite(int index){
		sprite = textures[index];
	}
}