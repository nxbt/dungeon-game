package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class HoverZone extends Hud {
	
	private String text;

	public HoverZone(World world, String text, float x, float y, int width, int height) {
		super(world, x, y, 32, 32, "slot.png");
		this.text = text;
		this.d_width = width;
		this.d_height = height;
	}

	@Override
	public void calc() {}
	
	public void hovered(){
		world.descBox.updateText(text);
	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch){
		
	}

}
