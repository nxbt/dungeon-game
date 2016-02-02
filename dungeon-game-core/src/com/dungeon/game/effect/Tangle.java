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
    		return "Your Tangled for  "+(int)Math.round(duration/60)+" secods!";
    	}
    	
    	public int getNum() {
    		return (int)duration;
    	}
}
