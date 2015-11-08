package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.Crap;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class Mouse extends Hud {
	
	public boolean pressed;
	private boolean down;
	
	public Slot slot;
	
	public Mouse(int x, int y) {
		super(x, y);
	}

	@Override
	public void init() {
		Gdx.input.setCursorCatched(true);
		
		sprite = new Texture("Crosshair.png");
		
		d_width = 16;
		d_height = 16;
		
		d_offx = -8;
		d_offy = -8;
		
		slot = new Slot(new int[] {0, 0, 0}, null);
		
		slot.item = new Crap();
	}

	@Override
	public void calc(World world) {
		
		x += Gdx.input.getDeltaX();
		y -= Gdx.input.getDeltaY();
		
		int screenWidth = (int) world.cam.WIDTH;
		int screenHeight = (int) world.cam.HEIGHT;
		
		if(x < 0) x = 0;
		else if(x > screenWidth) x = screenWidth;
		if(y < 0) y = 0;
		else if(y > screenHeight) y = screenHeight;
		
		if(Gdx.input.isButtonPressed(0) && !down) {
			down = true;
			pressed = true;
		}
		else if(!Gdx.input.isButtonPressed(0)) {
			down = false;
		}
		else {
			pressed = false;
		}
	}

	public void draw(SpriteBatch batch) {
		if(slot.item != null) batch.draw(slot.item.sprite, x-Item.SIZE/2, y-Item.SIZE/2, Item.SIZE, Item.SIZE);
		else batch.draw(sprite, x+d_offx, y+d_offy, d_width, d_height);
	}
}
