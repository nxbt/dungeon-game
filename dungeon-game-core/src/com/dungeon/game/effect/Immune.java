package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
import com.dungeon.game.world.World;

public class Immune extends Effect {
	public Immune(World world, Character character, int duration) {
		super(world, character, "Immune", duration);
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
