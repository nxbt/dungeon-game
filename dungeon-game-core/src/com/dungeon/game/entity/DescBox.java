package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class DescBox extends Hud {
	private final NinePatch DESC_BOX = new NinePatch(new Texture("desc_box.png"), 4, 4, 4, 4);
	
	public String text;
	
	BitmapFont desc;
	
	public DescBox() {
		super(0, 0);
	}

	@Override
	public void init() {
		d_width = 200;
		d_height = 100;
		
		d_offx = 16;
		d_offy = 0;
		
		x = 30;
		y = 30;
		
		text = "";
		
		desc = new BitmapFont();
		desc.setColor(Color.LIGHT_GRAY);
		desc.getData().setScale(1f);
	}

	@Override
	public void calc(World world) {
		text = "";
		
		x = world.mouse.x;
		y = world.mouse.y;
	}

	@Override
	public void draw(SpriteBatch batch) {
		if(!text.equals("")) {
			DESC_BOX.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
			
			desc.draw(batch, text, x+6, y+d_height-6);
		}
	}
}
