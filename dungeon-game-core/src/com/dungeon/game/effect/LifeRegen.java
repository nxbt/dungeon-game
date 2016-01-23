package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;
public class LifeRegen extends Effect {
	private float rate;
	
	public LifeRegen(World world, Character character, int duration, float rate) {
		super(world, character, "Healing", duration);
		this.rate = rate;
		texture = new Texture("heal.png");
		graphic = new EffectGraphic(this);
	}
	
	public LifeRegen(World world, Character character, int duration, int total) {
		super(world, character,"Healing", duration);
		this.rate = (float)total/(float)duration;
		texture = new Texture("heal.png");
		graphic = new EffectGraphic(this);
	}
	
	public void calc(Character character){
		character.gain_life(rate);
	}
	
	public String getHoveredText() {
		return "Recovering " + Math.round(rate*60f)+" health per second for "+Math.round(duration/6)/10f+" seconds";
	}
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}
}
