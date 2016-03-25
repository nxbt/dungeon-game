package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class HudSlot extends Hud {
	private Slot slot;
	
	private BitmapFont font;

	public HudSlot(World world, float x, float y, Slot slot) {
		super(world, x, y, 32, 32, "slot.png");
		
		this.slot = slot;
		
		sprite = slot.slotTex;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
	}
	
	public void hovered(){
		if(world.mouse.shift_down) {
			if(slot.item != null)world.descBox.updateText(slot.item);
			
			if(world.mouse.rb_pressed && slot.item != null) {
				DescWindow temp = new DescWindow(world, world.mouse.x, world.mouse.y);
				temp.updateText(slot.item);
				temp.open();
			}
		}
		else if(world.mouse.lb_pressed) {
			if(world.mouse.slot.item == null)slot.consume(world.player);
			else {
				slot.hovered();
				world.descBox.text = "";
			}
			
		}
	}

	@Override
	public void calc() {}

	@Override
	public void post() {}
	
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
