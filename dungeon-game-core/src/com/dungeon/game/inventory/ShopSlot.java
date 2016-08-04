package com.dungeon.game.inventory;

import com.dungeon.game.entity.hud.window.DescWindow;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.consumable.Consumable;
import com.dungeon.game.world.World;

public class ShopSlot extends Slot {
	
	private int cost;

	public ShopSlot(World world, int[] data, Inventory inv, int cost) {
		super(world, data, inv);
		this.cost = cost;
		// TODO Auto-generated constructor stub
	}
	
	public void hovered() {
			if(world.mouse.shift_down) {
				if(world.mouse.rb_pressed  && item != null) {
					DescWindow temp = new DescWindow(world, world.mouse.x, world.mouse.y);
					temp.updateText(item);
					temp.open();
				}
			}
			else if(item != null){
				if((world.mouse.lb_pressed||world.mouse.rb_pressed)&&item.maxStack == 1){
					if(world.player.spendGold(cost)){
						item = world.player.inv.addItem(item);
						if(item != null)world.player.gold+=cost;
					}
				}
				else if(world.mouse.lb_pressed) {
					if(world.player.gold >= cost) {
						int canBuy = Math.min(world.player.gold/cost,item.stack);
						Item temp = item.clone();
						temp.stack = canBuy;
						temp = world.player.inv.addItem(temp);
						if(temp != null){
							canBuy -= temp.stack;
						}
						item.stack-=canBuy;
						world.player.gold -= canBuy*cost;
					}
				}
				else if(world.mouse.rb_pressed) {
					if(world.player.spendGold(cost)){
						Item temp = item.clone();
						temp.stack = 1;
						if(world.player.inv.addItem(temp) == null)item.stack--;
						else world.player.gold+=cost;
					}
				}
				else if(world.mouse.mb_pressed) {
					if(item != null && item instanceof Consumable) {
						consume(world.player);
					}
				}
			}
		if(item != null){
			world.descBox.updateText(item);
			if(item.stack <= 0)item = null;
		}
	}

}
