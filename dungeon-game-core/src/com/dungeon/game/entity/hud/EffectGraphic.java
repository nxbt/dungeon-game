package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.world.World;

public class EffectGraphic extends Hud {
	Effect effect;
	
	private BitmapFont font;

	public EffectGraphic(World world, Effect effect) {
		super(world, 0, 0);
		this.effect = effect;
		sprite = Effect.texture;
		if (!sprite.getTextureData().isPrepared()) {
			sprite.getTextureData().prepare();
		}
		Pixmap tempMap = sprite.getTextureData().consumePixmap();
		

		Texture slot = new Texture("slot.png");
		if (!slot.getTextureData().isPrepared()) {
			slot.getTextureData().prepare();
		}
		Pixmap slotMap = slot.getTextureData().consumePixmap();
		slotMap.drawPixmap(tempMap, 0, 0);
		sprite = new Texture(slotMap);
		slotMap.dispose();
		tempMap.dispose();
		
		d_width = 32;
		d_height = 32;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.LIGHT_GRAY);
	}

	@Override
	public void init() {

	}

	@Override
	public void calc() {
		x = world.cam.WIDTH-36;
		y = world.cam.HEIGHT-36*(1+world.player.effectGraphics.indexOf(this));
	}

	@Override
	public void post() {

	}
	
	@Override
	public void hovered() {
		if(world.mouse.shift_down) {
			if(world.mouse.rb_pressed) {
				DescWindow temp = new DescWindow(world, world.mouse.x, world.mouse.y);
				temp.updateText(effect);
				temp.open();
			}
		}
		
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
