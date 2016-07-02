package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.utilities.TextHelper;
import com.dungeon.game.world.World;

public class TrainingWindow extends Window {
	
	private Slot slot;
	
	private Melee weapon;
	
	private BitmapFont font;
	
	private PartsInfo partsInfo;

	public TrainingWindow(World world, float x, float y) {
		super(world, x, y);
		
		d_width = 300;
		d_height = 220;
		
		slot = new Slot(world, new int[]{Item.HAND, 0, 0}, null);
		
		this.weapon = null;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
	}
	
	public void subCalc(){
		slot.x = x + 4;
		slot.y = y + d_height - 48;
		slot.calc();
		if(weapon != (Melee) slot.item){
			weapon = (Melee) slot.item;
			if(weapon == null) partsInfo = null;
			else partsInfo = new PartsInfo(world, 0, 0, weapon);
		}
		
		if(partsInfo != null){
			partsInfo.x = x + 4;
			partsInfo.y = y + 4;
			partsInfo.calc();
		}
	}
	
	public void subHovered(){
		if(world.mouse.x > slot.x && world.mouse.x < slot.x + Item.SIZE && world.mouse.y > slot.y && world.mouse.y < slot.y + Item.SIZE){
			slot.hovered();
			return;
		}
		if(partsInfo != null && world.mouse.x > partsInfo.x && world.mouse.x < partsInfo.x + partsInfo.d_width && world.mouse.y > partsInfo.y && world.mouse.y < partsInfo.y + partsInfo.d_height){
			partsInfo.hovered();
			return;
		}
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}
	
	public void subDraw(SpriteBatch batch){
		slot.draw(batch, 0, 0);
		if(weapon != null){
			font.draw(batch, weapon.name, x + 38, y + d_height - 26);
			String dmg = "" + Math.round(weapon.damage*10)/10f;
			String spd = "" + Math.round(weapon.speed*10)/10f;
			String knk = "" + Math.round(weapon.knockstr*10)/10f;
			font.draw(batch, "Damage:\nSpeed:\nKnockback:", x + 4, y + d_height - 50);
			font.draw(batch, dmg, TextHelper.alignRight(dmg, x + 144), y + d_height - 50);
			font.draw(batch, spd, TextHelper.alignRight(spd, x + 144), y + d_height - 66);
			font.draw(batch, knk, TextHelper.alignRight(knk, x + 144), y + d_height - 82);
			partsInfo.draw(batch);
		}
	}

}
