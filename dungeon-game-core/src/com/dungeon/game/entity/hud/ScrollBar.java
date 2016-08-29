package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.hud.window.Window;
import com.dungeon.game.world.World;

public class ScrollBar extends Hud {
	private static final NinePatch SCROLL_BAR = new NinePatch(new Texture("slot.png"), 2, 2, 2, 2);
	
	private Window window;

	public ScrollBar(World world, float x, float y, Window window) {
		super(world, x, y, 32, 32, "slot.png");
		this.window = window;
		dWidth = 5;
	}
	
	public void calc(){

		dHeight = (int) ((window.dHeight - 16)/window.contentHeight * (window.dHeight - 16));
		
		super.calc();
	}
	
	public void hovered(){
		super.hovered();
		
	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch){
		
	}

}
