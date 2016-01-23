package com.dungeon.game.effect;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.entity.hud.EffectGraphic;
import com.dungeon.game.world.World;

public class Inconspicuous extends Effect {

	public Inconspicuous(World world, int duration) {
		super(world, "Inconspicuous", duration);
		
		texture = new Texture("dizzy.png");
		graphic = new EffectGraphic(world, this);
	}

	public String getHoverText() {
		return "Conspicuously inconspicuous. Enemies ignore you.";
	}
}
