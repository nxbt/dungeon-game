package com.dungeon.game.inventory;

import java.util.ArrayList;

import com.dungeon.game.entity.hud.InvGraphic;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class Inventory {
	
	public Slot[] slot;
	
	public InvGraphic graphic;
	
	protected World world;
	
	public Inventory(World world, int[][] layout, float x, float y) {
		this.world = world;
		
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(world, layout[i], this);
		}
		
		this.graphic = new InvGraphic(world, this, x, y);
	}
	
	public Inventory(World world, int[][] layout, float x, float y, boolean isDialogue) {
		this.world = world;
		
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new DialogueSlot(world, layout[i], this);
		}
		
		this.graphic = new InvGraphic(world, this, x, y);
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
}
