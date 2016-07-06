package com.dungeon.game.effect.regen;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;
public class StamRegen extends Effect {
	private float rate;
	
	public StamRegen(World world, int duration, float rate) {
		super(world, "Stamina Regen", duration);
		this.rate = rate;
	}
	
	public StamRegen(World world, int duration, int total) {
		super(world, "Stamina Regen", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		character.gain_stam(rate);
	}
}
