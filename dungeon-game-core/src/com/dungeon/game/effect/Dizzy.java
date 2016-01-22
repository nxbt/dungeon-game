package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
public class Dizzy extends Effect {
	private float amount;
	private float dizzyness;
	
	public Dizzy(int duration, float amount) {
		super("Dizzy", duration);
		this.amount = amount;
	}
	
	public void calc(Character character){
	character.moveAngle += dizzyness;
	dizzyness += Math.random()*amount*2f-amount;
	}
}
