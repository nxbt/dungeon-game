package com.dungeon.game.effect;

import com.dungeon.game.item.consumable.Consumable;
import com.dungeon.game.world.World;

public class PotionSick extends Effect {
	
	public Consumable potion;

	public PotionSick(World world,  int duration, Consumable potion) {
		super(world, "Potion Sickness", duration);
		
		this.potion = potion;
	}

}
