package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class InvGraphic extends Hud {
	Slot[] slot;
	
	public InvGraphic(String sprite, Slot[] slot) {
		super(0, 0);
		
		this.slot = slot;
		
		this.sprite = new Texture(sprite);
	}

	@Override
	public void init() {
		x = 10;
		y = 100;
	}

	@Override
	public void calc(World world) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(sprite, x, y);
		
		for(Slot s: slot) {
			s.draw(batch, (int)x, (int)y);
		}
	}

}
