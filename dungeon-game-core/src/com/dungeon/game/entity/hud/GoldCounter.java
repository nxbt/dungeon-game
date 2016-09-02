package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class GoldCounter extends Hud {
	static final NinePatch BACKGROUND = new NinePatch(new Texture("goldCounter.png"), 0, 3, 11, 3);
	private int backgroundWidth;
	private int value;
	private BitmapFont font;
	public GoldCounter(World world) {
		super(world, 0, 0, 24, 24, "coinStack.png");
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.GOLD);
	}

	@Override
	public void calc() {
		if(value != 99999 && value < world.player.gold) {
			if(world.player.gold-value < 11) value += 1;
			else if(world.player.gold-value < 111) value += 11;
			else if(world.player.gold-value < 1111) value += 111;
			else value += 1111;
		}
		else if(value > world.player.gold) {
			if(value-world.player.gold < 11) value -= 1;
			else if(value-world.player.gold < 111) value -= 11;
			else if(value-world.player.gold < 1111) value -= 111;
			else value -= 1111;
		}
		if(value > 99999) value = 99999;
		backgroundWidth = (""+value).length()*8+9;
		x = world.cam.width-84-backgroundWidth;
		y = 76;
		super.calc();

	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch){
		super.draw(batch);
		BACKGROUND.draw(batch, x+24, y, backgroundWidth, dHeight-dOffY);
		font.draw(batch, ""+value, x+26, y+font.getScaleY()*12+2);
	}

}
