package com.dungeon.game.effect.regen;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;
public class StamRegen extends Effect {
	private float rate;
	
	private float curRate;
	
	public StamRegen(World world, int duration, float rate) {
		super(world, "Stamina Regen", duration);
		this.rate = rate;
		curRate = rate;
	}
	
	public StamRegen(World world, int duration, int total) {
		super(world, "Stamina Regen", duration);
		this.rate = (float)total/(float)duration;
	}
	
	public void calc(Character character){
		if(character.attacking) curRate = 0;
		
		character.gain_stam(curRate);
		
		if(curRate < rate) curRate += rate/180;
		else if(curRate > rate) curRate = rate;
	}
}
