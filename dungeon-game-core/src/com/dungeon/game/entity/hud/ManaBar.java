package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.dungeon.game.world.World;

public class ManaBar extends StatusBar {
	public ManaBar(int x, int y){
		super(x, y);
		filler = new TiledDrawable(new TextureRegion(new Texture("vial.png")));
	}

	@Override
	public void calc(World world) {
		cur = world.player.mana;
		max = world.player.maxMana;
	}
	
	public void post(World world) {}
}
