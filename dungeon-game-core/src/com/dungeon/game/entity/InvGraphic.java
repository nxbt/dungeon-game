package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.Inventory;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class InvGraphic extends Hud {
	Slot[] slot;
	
	Inventory inv;
	
	public InvGraphic(String sprite, Inventory inv, int x, int y) {
		super(x, y);
		
		this.slot = inv.slot;
		
		this.sprite = new Texture(sprite);
		
		this.inv = inv;
		
		d_width = this.sprite.getWidth();
		d_height = this.sprite.getHeight();
	}

	@Override
	public void init() {
	}

	@Override
	public void calc(World world) {
		inv.update(world);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(sprite, x, y, d_width, d_height);
		
		for(Slot s: slot) {
			s.draw(batch, (int)x, (int)y);
		}
	}
}
