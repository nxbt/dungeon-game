package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
public class ManaRegen extends Effect {
	private float rate;
	
	public ManaRegen(int duration, float rate) {
		super("Mana Regen", duration);
		this.rate = rate;
	}
	
	public ManaRegen(int duration, int total) {
		super("Mana Regen", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		character.gain_mana(rate);
	}
}
