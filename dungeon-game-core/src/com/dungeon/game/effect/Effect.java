package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.item.Item;
public abstract class Effect {
	
	public static Texture texture;
	
	public EffectGraphic graphic;

	private String name;
	
	public int duration;
	
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

	public String getHoveredText() {
		return null;
	}
	
	
}
