package com.dungeon.game.entity.hud;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.world.World;

public class EffectGraphic extends Hud {
	
	Effect effect;

	public EffectGraphic(Effect effect) {
		super(0, 0);
		this.effect = effect;
		sprite = effect.texture;
		// TODO Auto-generated constructor stub
		d_width = 32;
		d_height = 32;
	}

	@Override
	public void init() {

	}

	@Override
	public void calc(World world) {
		System.out.println("check");
		x = 0;
		y = 320-40*world.player.effectGraphics.indexOf(this);
	}

	@Override
	public void post(World world) {

	}
	
	@Override
	public void hovered(World world) {
		world.descBox.updateText(effect.getHoveredText());
	}
	
	public void open(World world) {
		world.hudEntities.add(0, this);
	}
	
	public void close(World world) {
		world.hudEntities.remove(this);
	}

}
