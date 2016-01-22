package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
public class StamRegen extends Effect {
	private float rate;
	
	public StamRegen(int duration, float rate) {
		super("Stamina Regen", duration);
		this.rate = rate;
	}
	
	public StamRegen(int duration, int total) {
		super("Stamina Regen", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		character.gain_stam(rate);
	}
}
