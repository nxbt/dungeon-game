package com.dungeon.game.effect.resistance;

import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class Immune extends Effect {
	public Immune(World world, int duration) {
		super(world, "Immune", duration);
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
