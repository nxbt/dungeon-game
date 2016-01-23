package com.dungeon.game.entity.hud;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class EffectGraphic extends Hud {
	
	Effect effect;
	
	private BitmapFont font;

	public EffectGraphic(World world, Effect effect) {
		super(world, 0, 0);
		this.effect = effect;
		sprite = effect.texture;
		// TODO Auto-generated constructor stub
		d_width = 32;
		d_height = 32;
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.DARK_GRAY);
	}

	@Override
	public void init() {

	}

	@Override
	public void calc() {
		System.out.println("check");
		x = 0;
		y = 320-40*world.player.effectGraphics.indexOf(this);
	}

	@Override
	public void post() {

	}
	
	@Override
	public void hovered() {
		world.descBox.updateText(effect.getHoveredText());
	}
	
	public void open() {
		world.hudEntities.add(0, this);
	}
	
	public void close() {
		world.hudEntities.remove(this);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x-origin_x+d_offx,/*y*/ y-origin_y+d_offy,/*originX*/origin_x,/*originY*/origin_y,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
		
		font.draw(batch, Integer.toString(effect.getNum()), (float) (x+32-font.getScaleX()*(Math.floor(Math.log10(effect.getNum()))+ 1)*7)-4, y+font.getScaleY()*12+1);

	}

}
