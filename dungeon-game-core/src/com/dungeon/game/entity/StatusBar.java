package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public abstract class StatusBar extends Hud {
	
	protected float percent;
	protected float max;
	protected float cur;
	
	protected TiledDrawable filler;
	
	public StatusBar(int x, int y){
		super(x, y);
	}
	
	@Override
	public void init() {
		width = 100;
		height = 20;
		d_width = 100;
		d_height = 10;
		percent = 1;
		sprite = new Texture("bar.png");
	}
	
	@Override 
	public void update(World world) {
		calc(world);
		
		percent = 1*cur/max;
		
		if(world.mouse.x > x && world.mouse.x < x+d_width && world.mouse.y > y && world.mouse.y < y+d_height) {
			world.descBox.updateText((int)cur + "/" + (int)max);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		filler.draw(batch, x, y, d_width*percent, d_height);
		batch.draw(sprite, x, y, d_width, d_height);
	}
}
