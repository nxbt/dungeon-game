package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
public class Healing extends Effect {
	private float rate;
	
	public Healing(int duration, float rate) {
		super("Healing", duration);
		this.rate = rate;
	}
	
	public Healing(int duration, int total) {
		super("Healing", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		character.heal(rate);
	}
}
