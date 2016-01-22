package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
public class LifeRegen extends Effect {
	private float rate;
	
	public LifeRegen(int duration, float rate) {
		super("Healing", duration);
		this.rate = rate;
		texture = new Texture("heal.png");
		graphic = new EffectGraphic(this);
	}
	
	public LifeRegen(int duration, int total) {
		super("Healing", duration);
		this.rate = (float)total/(float)duration;
		texture = new Texture("heal.png");
		graphic = new EffectGraphic(this);
	}
	
	public void calc(Character character){
		character.gain_life(rate);
	}
	
	public String getHoveredText() {
		return Math.round(rate*60f)+" hp/sec \n"+Math.round(duration/6)/10f+" secs";
	}
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}
}
