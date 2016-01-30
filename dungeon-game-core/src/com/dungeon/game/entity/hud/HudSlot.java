package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.Slot;
import com.dungeon.game.world.World;

public class HudSlot extends Hud {
	
	private Slot slot;
	
	private BitmapFont font;

	public HudSlot(World world, int x, int y, Slot slot) {
		super(world, x, y);
		this.slot = slot;
		d_width = 32;
		d_height = 32;
		sprite = new Texture("slot.png");
		

		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.LIGHT_GRAY);
	}

	@Override
	public void init() {
		

	}
	
	public void hovered(){
		if(world.mouse.shift_down) {
			if(slot.item != null)world.descBox.updateText(slot.item);
			
			if(world.mouse.rb_pressed && slot.item != null) {
				Window temp = new DescWindow(world, (int)world.mouse.x, (int)world.mouse.y, slot.item);
				temp.open();
			}
		}
		else if(world.mouse.lb_pressed)slot.consume(world.player);
	}

	@Override
	public void calc() {
		// TODO Auto-generated method stub

	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x-origin_x+d_offx,/*y*/ y-origin_y+d_offy,/*originX*/origin_x,/*originY*/origin_y,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
		if(slot.item!=null){
			batch.draw(/*Texture*/ slot.item.sprite,/*x*/ x-origin_x+d_offx,/*y*/ y-origin_y+d_offy,/*originX*/origin_x,/*originY*/origin_y,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),false,false);
			if(slot.item.stack > 1) {
				
				font.draw(batch, Integer.toString(slot.item.stack), (float) (x+Item.SIZE-font.getScaleX()*(Math.floor(Math.log10(slot.item.stack))+ 1)*7)-4, y+font.getScaleY()*12+1);
			}
		}

	}

}
