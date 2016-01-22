package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.hud.EffectGraphic;

public class Stun extends Effect {

	public Stun(int duration) {
		super("Stun", duration);
		texture = new Texture("stun.png");
		graphic = new EffectGraphic(this);
	}
	
	public void begin(Character character) {
		character.stun = true;
	}
	
	public void end(Character character) {
		for(Effect effect: character.effects) {
			if(effect instanceof Stun&&!effect.equals(this)) return;
		}
		character.stun = false;
	}
	
	public String getHoveredText() {
		return Math.round(duration/6)/10f+" secs";
	}
	

}
