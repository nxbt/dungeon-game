package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.dungeon.game.world.World;

public class ManaBar extends StatusBar {
	public ManaBar(World world, float x, float y){
		super(world, x, y);
		filler = new TiledDrawable(new TextureRegion(new Texture("vial.png")));
	}

	@Override
	public void calc() {
		cur = world.player.mana;
		max = world.player.maxMana;
		super.calc();
	}
	
	public void post() {}
}
