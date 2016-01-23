package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
import com.dungeon.game.world.World;
public class StamRegen extends Effect {
	private float rate;
	
	public StamRegen(World world, Character character, int duration, float rate) {
		super(world, character, "Stamina Regen", duration);
		this.rate = rate;
	}
	
	public StamRegen(World world, Character character, int duration, int total) {
		super(world, character, "Stamina Regen", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		character.gain_stam(rate);
	}
}
