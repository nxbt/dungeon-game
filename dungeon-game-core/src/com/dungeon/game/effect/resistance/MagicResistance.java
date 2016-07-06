package com.dungeon.game.effect.resistance;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class MagicResistance extends Effect {
	
	private int amount;

	public MagicResistance(World world, int duration, int amount) {
		super(world, "Magic Resistance", duration);
		
		this.amount = amount;
		
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(world, this);
	}
	
	public MagicResistance(World world, int amount) { //for use in armor so you cant see the effect :o
		super(world, "Magic Resistance", -1);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		character.arcan_resist+=amount;
	}
	
	public void end(Character character){
		character.arcan_resist-=amount;
	}
	
	public String getHoveredText() {
		return "You have " + Math.round(amount) + " extra magic resistance";
	}
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}

}
