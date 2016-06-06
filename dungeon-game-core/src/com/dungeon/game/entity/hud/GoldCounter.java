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
	private String value;
	private BitmapFont font;
	public GoldCounter(World world) {
		super(world, 0, 0, 24, 24, "coinStack.png");
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.GOLD);
	}

	@Override
	public void calc() {
		if(world.player.gold<99999)value = ""+world.player.gold;
		else value = "99999";
		backgroundWidth = value.length()*8+9;
		x = world.cam.width-84-backgroundWidth;
		y = 76;

	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch){
		batch.draw(/*Texture*/ sprite,/*x*/ x-origin_x+d_offx,/*y*/ y-origin_y+d_offy,/*originX*/origin_x,/*originY*/origin_y,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),flipX,flipY);
		BACKGROUND.draw(batch, x+24, y, backgroundWidth, d_height-d_offy);
		font.draw(batch, value, x+26, y+font.getScaleY()*12+2);
	}

}
