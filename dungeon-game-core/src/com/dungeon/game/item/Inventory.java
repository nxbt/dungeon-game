package com.dungeon.game.item;

import java.util.ArrayList;

import com.dungeon.game.entity.hud.InvGraphic;
import com.dungeon.game.world.World;

public class Inventory {
	
	public Slot[] slot;
	
	public InvGraphic graphic;
	
	public Inventory(int[][] layout, String graphic, int x, int y, int dragX, int dragY, int dragWidth, int dragHeight) {
		
		slot = new Slot[layout.length];
		
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(layout[i], this);
		}
		
		this.graphic = new InvGraphic(graphic, this, x, y, dragX, dragY, dragWidth, dragHeight);
	}
	
	public void hovered(World world) {
		for(Slot s: slot) {
			if(s.x + graphic.x < world.mouse.x && s.x + graphic.x + Item.SIZE > world.mouse.x && s.y + graphic.y < world.mouse.y && s.y + graphic.y + Item.SIZE > world.mouse.y) s.hovered(world);
		}
	}
	
	public void calc(World world) {
		for(Slot s: slot) {
			s.update(world);
		}
	}
	
	public void update(World world) {
		calc(world);
	}
	
	public Slot contains(Item item) {
		for(Slot s: slot) {
			if(s.item != null && s.item.getClass().equals(item.getClass())) return s;
		}
		
		return null;
	}
	
	public Item addItem(Item item) {
		for(Slot s: slot) {
			if(s.item != null && s.item.getClass().equals(item.getClass()) && s.item.stack < s.item.maxStack) {
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
				s.item = item.clone();
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
				
				if(s.item.stack>0) {
					if(s.item instanceof Weapon) {
						((Weapon) s.item).owner = null;
					}
					drops.add(s.item);
				}
			}
		}
		return drops;
	}
}
