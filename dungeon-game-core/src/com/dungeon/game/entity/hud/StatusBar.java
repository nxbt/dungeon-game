package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.dungeon.game.world.World;

public abstract class StatusBar extends Hud {
	
	protected float percent;
	protected float max;
	protected float cur;
	
	protected TiledDrawable filler;
	
	public StatusBar(World world, float x, float y){
		super(world, x, y, 8, 68, "statusbar.png");
		
		percent = 1;
	}
	
	@Override 
	public void update() {
		
		percent = 1*cur/max;
		super.update();
	}
	
	@Override
	public void hovered() {
		super.hovered();
		world.descBox.updateText((int)cur + "/" + (int)max);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		filler.draw(batch, x, y, d_width, d_height*percent);
		batch.draw(sprite, x, y, d_width, d_height);
	}
}
