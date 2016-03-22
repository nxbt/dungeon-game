package com.dungeon.game.inventory;

import com.dungeon.game.entity.hud.DescWindow;
import com.dungeon.game.item.Consumable;
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
			else if(item != null&&(world.mouse.slot.item == null || world.mouse.slot.item.name.equals(item.name))){
				if((world.mouse.lb_pressed||world.mouse.rb_pressed)&&item.maxStack == 1){
					if(world.mouse.slot.item == null&&world.player.spendGold(cost)){
						swap(world.mouse.slot);
					}
				}
				else if(world.mouse.lb_pressed) {
//					while(this.item.stack > 0 && world.player.spendGold(cost)){
//						if(world.mouse.slot.item == null){
//							world.mouse.slot.item = this.item.clone();
//							world.mouse.slot.item.stack = 1;
//						}
//						else world.mouse.slot.item.stack++;
//						item.stack--;
//					}
					if(world.player.gold >= cost) {
						int canBuy = world.player.gold/cost;
						if(world.mouse.slot.item == null) {
							world.mouse.slot.item = item.clone();
							world.mouse.slot.item.stack = Math.min(canBuy, item.stack);
							item.stack-=world.mouse.slot.item.stack;
							world.player.gold -= world.mouse.slot.item.stack*cost;
						}else{
							int num = Math.min(canBuy, item.stack);
							world.mouse.slot.item.stack+=num;
							item.stack-=num;
							world.player.gold -= num*cost;
							
						}
					}
				}
				else if(world.mouse.rb_pressed) {
					if(world.player.spendGold(cost)){
						if(world.mouse.slot.item == null){
							world.mouse.slot.item = this.item.clone();
							world.mouse.slot.item.stack = 1;
						}
						else world.mouse.slot.item.stack++;
						item.stack--;
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
