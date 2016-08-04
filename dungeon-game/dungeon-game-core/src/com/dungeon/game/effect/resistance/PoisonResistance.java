package com.dungeon.game.effect.resistance;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class PoisonResistance extends Effect {
	
	private float amount;

	public PoisonResistance(World world, int duration, float amount) {
		super(world, "Poison Resistance", duration);
		
		this.amount = amount;
		
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(world, this);
	}
	
	public PoisonResistance(World world, float amount) { //for use in armor so you cant see the effect :o
		super(world, "Poison Resistance", -1);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		character.poisn_resist+=amount;
	}
	
	public void end(Character character){
		character.poisn_resist-=amount;
	}
	
	public String getHoveredText() {
		return "You have " + Math.round(amount) + " extra poison resistance";
	}
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}

}
