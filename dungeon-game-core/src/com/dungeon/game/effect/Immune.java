package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;

public class Immune extends Effect {
	public Immune(int duration) {
		super("Immune", duration);
	}
	
	public void begin(Character character) {
		character.immune = true;
	}
	
	public void end(Character character) {
		for(Effect effect: character.effects){
			if(effect instanceof Immune&&!effect.equals(this)) return;
		}
		character.immune = false;
	}
}
