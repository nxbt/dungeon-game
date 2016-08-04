package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;
public class Dizzy extends Effect {
	private float amount;
	private float dizzyness;
	
	public Dizzy(World world, int duration, float amount) {
		super(world, "Dizzy", duration);
		
		this.amount = amount;
		
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(world, this);
	}
	
	public void calc(Character character){
		if(character.move_angle!=361)character.move_angle += dizzyness;
		dizzyness += Math.random()*amount*2f-amount;
		if(dizzyness>amount*10)dizzyness = amount*10;
		if(dizzyness<-amount*10)dizzyness = -amount*10;
	}
		
	public String getHoveredText() {
			return "You are dizzy for the next " + Math.round(duration/6)/10f + " seconds, it's hard to move!";
	}
		
	public int getNum() {
			return (int) Math.ceil(duration/60f);
	}
}
