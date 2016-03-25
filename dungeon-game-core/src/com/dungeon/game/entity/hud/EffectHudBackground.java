package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class EffectHudBackground extends Hud {

	static final NinePatch BACKGROUND = new NinePatch(new Texture("effectBackground.png"), 8, 4, 4, 8);

	public EffectHudBackground(World world, float x, float y) {
		super(world, x, y, 32, 32, "slot.png");
		d_width = 44;
	}

	@Override
	public void calc() {
		if(world.player.effectGraphics.size()>0)d_height = world.player.effectGraphics.size()*36+8;
		else d_height = 0;
		y = world.cam.HEIGHT - d_height; 

	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch){
		if(d_height>0)BACKGROUND.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
	}

}
