package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
public class LifeRegen extends Effect {
	private float rate;
	
	public LifeRegen(int duration, float rate) {
		super("Healing", duration);
		this.rate = rate;
	}
	
	public LifeRegen(int duration, int total) {
		super("Healing", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		character.gain_life(rate);
	}
}
