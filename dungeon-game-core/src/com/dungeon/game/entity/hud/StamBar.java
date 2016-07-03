package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.dungeon.game.world.World;

public class StamBar extends StatusBar {
	public StamBar(World world, float x, float y){
		super(world, x, y);
		filler = new TiledDrawable(new TextureRegion(new Texture("energy.png")));
	}

	@Override
	public void calc() {
		cur = world.player.stam;
		max = world.player.maxStam;
		super.calc();
	}
	
	public void post() {}
}
