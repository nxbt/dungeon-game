package com.dungeon.game.entity;

import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.hud.DescWindow;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Gold;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class Drop extends Static {
	
	Slot slot;
	
	public Drop(World world, float x, float y, Slot slot) {
		super(world, x, y, Item.SIZE, Item.SIZE, "slot.png");
		
		this.slot = new Slot(world, new int[] {0,0,0}, null);
		
		this.slot.swap(slot);
		
		sprite = this.slot.item.sprite;
		
		hitbox = new Polygon(new float[]{4,4,28,4,28,28,4,28});
		
		origin_x =16;
		origin_y = 16;
		
		angle = (float) (180f-Math.random()*360);
	}
	
	@Override
	public void hovered(){
		if(world.mouse.canPickup) {
			if(slot.item instanceof Gold && !(world.mouse.shift_down && world.mouse.rb_pressed)) {
				if(world.mouse.lb_pressed || world.mouse.rb_pressed) {
					world.player.gold += slot.item.stack;
					slot.item = null;
				}
			}
			if(world.mouse.shift_down && slot.item != null) {
				world.descBox.updateText(slot.item);
				
				if(world.mouse.lb_pressed) {
					slot.item = world.player.inv.addItem(slot.item);
				}
				else if(world.mouse.rb_pressed && slot.item != null) {
					DescWindow temp = new DescWindow(world, world.mouse.x, world.mouse.y);
					temp.updateText(slot.item);
					temp.open();
				}
			}
			else if(world.mouse.lb_pressed) {
				if(slot.item != null && world.mouse.slot.item != null && world.mouse.slot.item.name.equals(slot.item.name)) {
					if(slot.item.stack + world.mouse.slot.item.stack <= slot.item.maxStack) {
						slot.item.stack+=world.mouse.slot.item.stack;
						world.mouse.slot.item = null;
					}
					else {
						world.mouse.slot.item.stack = slot.item.stack+world.mouse.slot.item.stack-slot.item.maxStack;
						slot.item.stack = slot.item.maxStack;
					}
				}
				else slot.swap(world.mouse.slot);
			}
			else if(world.mouse.rb_pressed) {
				if(slot.item != null && world.mouse.slot.item != null && world.mouse.slot.item.name.equals(slot.item.name)) {
					if(world.mouse.slot.item.stack < slot.item.maxStack) {
						slot.item.stack--;
						world.mouse.slot.item.stack++;
					}
				}
				else if(slot.item != null && world.mouse.slot.item == null) {
					world.mouse.slot.item = slot.item;
					slot.item = world.mouse.slot.item.clone();
					slot.item.stack--;
					world.mouse.slot.item.stack = 1;
				}
				else slot.swap(world.mouse.slot);
				if(slot.item != null && slot.item.stack == 0) slot.item = null;
				if(world.mouse.slot.item != null && world.mouse.slot.item.stack == 0) world.mouse.slot.item = null;
			}
		}
		
		if(slot.item == null) killMe = true;
		else sprite = slot.item.sprite;
	}

	@Override
	public void calc() {}

	public void post() {}
}
