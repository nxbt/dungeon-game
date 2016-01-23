package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class Stun extends Effect {

	public Stun(World world, Character character, int duration) {
		super(world, character, "Stun", duration);
		texture = new Texture("stun.png");
		graphic = new EffectGraphic(world, this);
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
	
	public int getNum() {
		return (int) Math.ceil(duration/60f);
	}
	

}
