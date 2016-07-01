package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.weapon.Melee;
import com.dungeon.game.world.World;

public class TrainingWindow extends Window {
	
	private HudSlot slot;
	
	private Melee weapon;
	
	private BitmapFont font;

	public TrainingWindow(World world, float x, float y) {
		super(world, x, y);
		
		d_width = 300;
		d_height = 200;
		
		slot = new HudSlot(world,0,0,new Slot(world, new int[]{Item.HAND, 0, 0}, null));
		
		this.weapon = null;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
	}
	
	public void subCalc(){
		slot.x = x + 4;
		slot.y = y + d_height - 48;
		slot.calc();
		weapon = (Melee) slot.slot.item;
	}
	
	public void subHovered(){
		if(world.mouse.x > this.slot.x && world.mouse.x < this.slot.x + this.slot.d_width && world.mouse.y > this.slot.y && world.mouse.y < this.slot.y + this.slot.d_height){
			slot.hovered();
		}
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}
	
	public void subDraw(SpriteBatch batch){
		slot.draw(batch);
		if(weapon != null){
			font.draw(batch, weapon.name, x + 38, y + d_height - 26);
			String dmg = "" + Math.round(weapon.damage*10)/10f;
			String spd = "" + Math.round(weapon.speed*10)/10f;
			String knk = "" + Math.round(weapon.knockstr*10)/10f;
			font.draw(batch, "Damage:\nSpeed:\nKnockback:", x + 4, y + d_height - 50);
			font.draw(batch, dmg, x + 144 - 8*dmg.length(), y + d_height - 50);
			font.draw(batch, spd, x + 144 - 8*spd.length(), y + d_height - 66);
			font.draw(batch, knk, x + 144 - 8*knk.length(), y + d_height - 82);
		}
	}

}
