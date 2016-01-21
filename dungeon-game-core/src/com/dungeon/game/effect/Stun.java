package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;

public class Stun extends Effect {

	public Stun(int duration) {
		super("Stun", duration);
	}
	
	public void begin(Character character){
		character.stun = true;
	}
	
	public void end(Character character){
		boolean end = true;
		for(Effect effect: character.effects){
			if(effect instanceof Stun&&!effect.equals(this)){
				end = false;
				break;
			}
		}
		if(end)character.stun = false;
	}
	

}
