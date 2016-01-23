package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
import com.dungeon.game.world.World;
public class ManaRegen extends Effect {
	private float rate;
	
	public ManaRegen(World world, Character character, int duration, float rate) {
		super(world, character, "Mana Regen", duration);
		this.rate = rate;
	}
	
	public ManaRegen(World world, Character character, int duration, int total) {
		super(world, character, "Mana Regen", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		character.gain_mana(rate);
	}
}
