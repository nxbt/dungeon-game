package com.dungeon.game.effect;

import com.dungeon.game.entity.Character;
public abstract class Effect {

	private String name;
	
	private int duration;
	
	public boolean killMe;
	
	public Effect(String name, int duration){
		this.name = name;
		this.duration = duration;
	}
	
	public void update(Character character){
		calc(character);
		if(duration>0)duration--;
		else if(duration==0){
			killMe = true;
		}
	}
	
	protected void calc(Character character){}
	
	public void begin(Character character){}
	
	public void end(Character character){}
	
	
}
