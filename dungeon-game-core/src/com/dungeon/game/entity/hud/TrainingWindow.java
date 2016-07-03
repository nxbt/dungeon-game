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
		d_height = 232;
		
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
//		if(partsInfo != null && world.mouse.x > partsInfo.x && world.mouse.x < partsInfo.x + partsInfo.d_width && world.mouse.y > partsInfo.y && world.mouse.y < partsInfo.y + partsInfo.d_height){
//			partsInfo.hovered();
//			return;
//		}
		if(partsInfo != null && partsInfo.isHovered()){
			partsInfo.hovered();
			return;
		}
		if(weapon != null && world.mouse.x > x + 4 && world.mouse.x < x + 160){
			if(world.mouse.y >  y + d_height - 66 && world.mouse.y <  y + d_height - 50){
				world.descBox.updateText("Weapon Damage: " + Math.round(weapon.damage*10)/10f + "\nBase Damage: " + Math.round(weapon.baseDamage*10)/10f);
				return;
			}
			else if(world.mouse.y >  y + d_height - 84 && world.mouse.y <  y + d_height - 66){
				world.descBox.updateText("Weapon Speed: " + Math.round(weapon.speed*10)/10f + "\nBase Speed: " + Math.round(weapon.baseSpeed*10)/10f);
				return;
			}
			else if(world.mouse.y >  y + d_height - 100 && world.mouse.y <  y + d_height - 84){
				world.descBox.updateText("Weapon Knockback: " + Math.round(weapon.knockstr*10)/10f + "\nBase Knockback: " + Math.round(weapon.baseKnock*10)/10f);
				return;
			}
			else if(world.mouse.y >  y + d_height - 116 && world.mouse.y <  y + d_height - 100){
				world.descBox.updateText("Weapon Weight: " + Math.round(weapon.weight*10)/10f + "\nBase Weight: " + Math.round(weapon.baseWeight*10)/10f);
				return;
			}
		}
	}

	@Override
	public void post() {}
	
	public void subDraw(SpriteBatch batch){
		slot.draw(batch, 0, 0);
		if(weapon != null){
			font.draw(batch, weapon.name, x + 38, y + d_height - 26);
			String dmg = "" + Math.round(weapon.damage*10)/10f;
			String spd = "" + Math.round(weapon.speed*10)/10f;
			String knk = "" + Math.round(weapon.knockstr*10)/10f;
			String wgt = "" + Math.round(weapon.weight*10)/10f;
			font.draw(batch, "Base Damage:\nBase Speed:\nBase Knock:\nBase Weight:", x + 4, y + d_height - 50);
			font.draw(batch, dmg, TextHelper.alignRight(dmg, x + 160), y + d_height - 50);
			font.draw(batch, spd, TextHelper.alignRight(spd, x + 160), y + d_height - 66);
			font.draw(batch, knk, TextHelper.alignRight(knk, x + 160), y + d_height - 82);
			font.draw(batch, wgt, TextHelper.alignRight(wgt, x + 160), y + d_height - 98);
			partsInfo.draw(batch);
		}
	}

}
