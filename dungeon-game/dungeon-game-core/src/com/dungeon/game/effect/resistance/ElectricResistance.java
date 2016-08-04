package com.dungeon.game.effect.resistance;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class ElectricResistance extends Effect {
	
	private float amount;

	public ElectricResistance(World world, int duration, float amount) {
		super(world, "Electric Resistance", duration);
		
		this.amount = amount;
		
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(world, this);
	}
	
	public ElectricResistance(World world, float amount) { //for use in armor so you cant see the effect :o
		super(world, "Electric Resistance", -1);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		float num = 0;
		float total = 0;
		for(Effect e: character.effects){
			if(e instanceof ElectricResistance){
				num++;
				total+=((ElectricResistance) e).amount;
			}
		}
		character.ligtn_resist = total / num;
	}
	
	public void end(Character character){
		float num = 0;
		float total = 0;
		for(Effect e: character.effects){
			if(e instanceof ElectricResistance){
				num++;
				total+=((ElectricResistance) e).amount;
			}
		}
		character.ligtn_resist = total / num;
	}
	
	public String getHoveredText() {
		return "You have " + Math.round(amount) + " extra electric resistance";
	}
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}

}
