package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
public class Dizzy extends Effect {
	private float amount;
	private float dizzyness;
	
	public Dizzy(int duration, float amount) {
		super("Dizzy", duration);
		this.amount = amount;
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(this);
	}
	
	public void calc(Character character){
	if(character.move_angle!=361)character.move_angle += dizzyness;
	dizzyness += Math.random()*amount*2f-amount;
	if(dizzyness>amount*10)dizzyness = amount*10;
	if(dizzyness<-amount*10)dizzyness = -amount*10;
	}
	
	public String getHoveredText() {
		return Math.round(duration/6)/10f+" secs";
	}
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}
}
