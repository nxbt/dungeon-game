package com.dungeon.game.effect.resistance;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class FireResistance extends Effect {
	
	private float amount;

	public FireResistance(World world, int duration, float amount) {
		super(world, "Fire Resistance", duration);
		
		this.amount = amount;
		
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(world, this);
	}
	
	public FireResistance(World world, float amount) { //for use in armor so you cant see the effect :o
		super(world, "Fire Resistance", -1);
		
		this.amount = amount;
	}
	
	public void begin(Character character){
		float num = 0;
		float total = 0;
		for(Effect e: character.effects){
			if(e instanceof FireResistance){
				num++;
				total+=((FireResistance) e).amount;
			}
		}
		character.flame_resist = total / num;
	}
	
	public void end(Character character){
		float num = 0;
		float total = 0;
		for(Effect e: character.effects){
			if(e instanceof FireResistance){
				num++;
				total+=((FireResistance) e).amount;
			}
		}
		character.flame_resist = total / num;
	}
	
	public String getHoveredText() {
		return "You have " + Math.round(amount) + " extra fire resistance";
	}
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}

}
