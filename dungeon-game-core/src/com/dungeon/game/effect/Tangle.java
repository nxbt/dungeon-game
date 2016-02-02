package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
import com.dungeon.game.world.World;

public class Tangle extends Effect {
	public Immune(World world, int duration) {
		super(world, "Tangled", duration);
		texture = new Texture("tangle.png");
		graphic = new EffectGraphic(world, this);
	}
	
	public void calc(Character character){
		character.moveAngle = 361;
	}
	
	public String getHoveredText() {
    		return "You're tangled for "+(int)Math.round(duration/60)+" seconds. You can't move!";
    	}
    	
    	public int getNum() {
    		return (int)duration;
    	}
}
