package com.dungeon.game.inventory;

import com.dungeon.game.entity.hud.DescWindow;
import com.dungeon.game.item.Consumable;
import com.dungeon.game.item.Gold;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class DialogueSlot extends Slot {

	public DialogueSlot(World world, int[] data, Inventory inv) {
		super(world, data, inv);
	}
	
	public void hovered() {
		if(item instanceof Gold && !(world.mouse.shift_down && world.mouse.rb_pressed)) {
			if(world.mouse.lb_pressed || world.mouse.rb_pressed) {
				world.player.gold += item.stack;
				item = null;
			}
		}
		else if(item!=null){
			if(world.mouse.lb_pressed){
				item = world.player.inv.addItem(item);
			}else if(world.mouse.rb_pressed){
				if(item.stack == 1){
					item = world.player.inv.addItem(item);
				}else{
					Item temp = item.clone();
					temp.stack = 1;
					if(world.player.inv.addItem(temp) == null)item.stack--;
				}
			}
		}
		if(item != null)world.descBox.updateText(item);
	}

}
