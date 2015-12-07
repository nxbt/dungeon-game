package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.dungeon.game.world.World;

public class StanimaBar extends StatusBar {
	public StanimaBar(int x, int y){
		super(x, y);
		filler = new TiledDrawable(new TextureRegion(new Texture("energy.png")));
	}

	@Override
	public void calc(World world) {
		percent = 1*world.player.stanima/world.player.maxStanima;
	}
}
