package com.dungeon.game.effect;

import com.dungeon.game.item.Consumable;

public class PotionSick extends Effect {
	
	public Consumable potion;

	public PotionSick( int duration, Consumable potion) {
		super("Potion Sickness", duration);
		// TODO Auto-generated constructor stub
		this.potion = potion;
	}

}
