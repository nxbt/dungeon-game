package com.dungeon.game.inventory;

import java.util.ArrayList;
import java.util.Collections;

import com.dungeon.game.entity.hud.window.InvWindow;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class Inventory {
	
	public static final int[][]  DEFAULT_LAYOUT = new int[][]{
		//consumables
		new int[] {1, 8, 8}, //1
		new int[] {1, 48, 8}, //2
		new int[] {1, 88, 8}, //3
		new int[] {1, 128, 8}, //4
		new int[] {1, 168, 8}, //5
		//inventory
		new int[] {0, 8, 48}, //6
		new int[] {0, 48, 48}, //7
		new int[] {0, 88, 48}, //8
		new int[] {0, 128, 48}, //9
		new int[] {0, 168, 48}, //10
		new int[] {0, 8, 88}, //11
		new int[] {0, 48, 88}, //12
		new int[] {0, 88, 88}, //13
		new int[] {0, 128, 88}, //14
		new int[] {0, 168, 88}, //15
		new int[] {0, 8, 128}, //16
		new int[] {0, 48, 128}, //17
		new int[] {0, 88, 128}, //18
		new int[] {0, 128, 128}, //19
		new int[] {0, 168, 128}, //20
		new int[] {0, 8, 168}, //21
		new int[] {0, 48, 168}, //22
		new int[] {0, 88, 168}, //23
		new int[] {0, 128, 168}, //24
		new int[] {0, 168, 168}, //25
		new int[] {0, 8, 208}, //26
		new int[] {0, 48, 208}, //27
		new int[] {0, 88, 208}, //28
		new int[] {0, 128, 208}, //29
		new int[] {0, 168, 208}, //30
		//weapons
		new int[] {2, 208, 8}, //31
		new int[] {2, 248, 8}, //32
		//Armor
		new int[] {7, 208, 48}, //33
		new int[] {6, 208, 88}, //34
		new int[] {5, 208, 128}, //35
		new int[] {4, 208, 168}, //36
		new int[] {3, 208, 208}, //37
		//Rings and Amulet
		new int[] {9, 248, 48}, //38
		new int[] {9, 248, 88}, //39
		new int[] {9, 248, 128}, //40
		new int[] {9, 248, 168}, //41
		new int[] {8, 248, 208}, //42
	};
	
	public Slot[] slot;
	
	public InvWindow graphic;
	
	protected World world;
	
	public Inventory(World world, int[][] layout, float x, float y) {
		this.world = world;
		
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(world, layout[i], this);
		}
		
		this.graphic = new InvWindow(world, this, x, y);
	}
	
	public Inventory(World world, int[][] layout, float x, float y, boolean isDialogue) {
		this.world = world;
		
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new DialogueSlot(world, layout[i], this);
		}
		
		this.graphic = new InvWindow(world, this, x, y);
	}
	
	public void hovered() {
		for(Slot s: slot) {
			if(s.x + graphic.x < world.mouse.x && s.x + graphic.x + Item.SIZE > world.mouse.x && s.y + graphic.y < world.mouse.y && s.y + graphic.y + Item.SIZE > world.mouse.y) s.hovered();
		}
	}
	
	public void calc() {
		for(Slot s: slot) {
			s.update();
		}
	}
	
	public void update() {
		calc();
	}
	
	public Slot contains(Item item) {
		return contains(item, 1);
	}
	
	public Slot contains(Item item, int stackSize) {
		for(Slot s: slot) {
			if(s.item != null && s.item.getClass().equals(item.getClass())) {
				stackSize--;
				if(stackSize <= 0) return s;
			}
		}
		
		return null;
	}
	
	public Item addItem(Item item, int count, float dropChance) {
		item.dropChance = dropChance;
		
		return addItem(item, count);
	}
	
	public Item addItem(Item item, int count) {
		item.stack = count;
		
		return addItem(item);
	}
	
	public Item addItem(Item item, float dropChance) {
		item.dropChance = dropChance;
		
		return addItem(item);
	}
	
	public Item addItem(Item item) {
		for(Slot s: slot) {
			if(s.item != null && !s.item.equals(item) && s.item.getClass().equals(item.getClass()) && s.item.stack < s.item.maxStack) {
				if(s.item.stack + item.stack > s.item.maxStack) {
					item.stack -= s.item.maxStack-s.item.stack;
					s.item.stack = s.item.maxStack;
				}
				else {
					s.item.stack += item.stack;
					item = null;
					break;
				}
				
			}
		}
		if(item != null) for(Slot s: slot) {
			if(s.item == null && s.type == 0) {
				s.item = item;
				item = null;
				break;
			}
		}
		
		return item;
	}
	
	public Item addItemRand(Item item, int count){
		item.stack = count;
		return addItemRand(item);
		
	}
	
	public Item addItemRand(Item item){
		ArrayList<Slot> slot = new ArrayList<Slot>();
		for(Slot s: this.slot){
			if(s.item == null || s.item.getClass().equals(item.getClass()))slot.add(s);
		}
		Collections.shuffle(slot);
		for(Slot s: slot) {
			if(s.item != null && !s.item.equals(item) && s.item.getClass().equals(item.getClass()) && s.item.stack < s.item.maxStack) {
				if(s.item.stack + item.stack > s.item.maxStack) {
					item.stack -= s.item.maxStack-s.item.stack;
					s.item.stack = s.item.maxStack;
				}
				else {
					s.item.stack += item.stack;
					item = null;
					break;
				}
				
			}
		}
		if(item != null) for(Slot s: slot) {
			if(s.item == null && s.type == 0) {
				s.item = item;
				item = null;
				break;
			}
		}
		return item;
	}
	
	public ArrayList<Item> getDrops(){
		ArrayList<Item> drops = new ArrayList<Item>();
		for(Slot s: slot){
			if(s.item!=null){
				int stack = 0;
				for(int i = 0; i < s.item.stack;i++){
					if(Math.random()<s.item.dropChance) stack++;
				}
				
				s.item.stack = stack;
				
				if(s.item.stack>0) drops.add(s.item);
			}
		}
		return drops;
	}

	public boolean isEmpty() {
		for(Slot s: slot){
			if(s.item != null)return false;
		}
		return true;
	}
}
