package com.dungeon.game.entity.hud.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.hud.HoverZone;
import com.dungeon.game.entity.hud.PartsInfo;
import com.dungeon.game.entity.hud.SwingSelection;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.item.equipable.weapon.Melee;
import com.dungeon.game.utilities.TextHelper;
import com.dungeon.game.world.World;

public class TrainingWindow extends Window {
	
	private Slot slot;
	
	private Melee weapon;
	
	private BitmapFont font;

	public TrainingWindow(World world, float x, float y) {
		super(world, x, y);
		
		d_width = 300;
		d_height = 232;
		
		slot = new Slot(world, new int[]{Item.HAND, 0, 0}, null);
		
		this.weapon = null;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
	}
	
	public void calc(){
		if(weapon != (Melee) slot.item){
			if(weapon!=null){
				((SwingSelection) getSubEntity("swingSelection")[0]).setSwings();
			}
			weapon = (Melee) slot.item;
			removeSubEntity("partsInfo");
			removeSubEntity("swingSelection");
			removeSubEntity("statDesc");
			if(weapon != null){
				addSubEntitiy(new PartsInfo(world, 0, 0, weapon),  "partsInfo", 4, 4);
				addSubEntitiy(new SwingSelection(world, 0, 0, weapon),  "swingSelection", 168, 4);
				addSubEntitiy(new HoverZone(world, weapon.getDamageText(), 0, 0, 156, 16),  "statDesc", 4, d_height - 66);
				addSubEntitiy(new HoverZone(world, weapon.getSpeedText(), 0, 0, 156, 16),  "statDesc", 4, d_height - 84);
				addSubEntitiy(new HoverZone(world, weapon.getKnockText(), 0, 0, 156, 16),  "statDesc", 4, d_height - 100);
				addSubEntitiy(new HoverZone(world, weapon.getWeightText(), 0, 0, 156, 16),  "statDesc", 4, d_height - 116);
			}
		}
		
		super.calc();
		
		slot.x = x + 4;
		slot.y = y + d_height - 48;
		slot.calc();
	}
	
	public void hovered(){
		super.hovered();
		if(world.mouse.x > slot.x && world.mouse.x < slot.x + Item.SIZE && world.mouse.y > slot.y && world.mouse.y < slot.y + Item.SIZE){
			slot.hovered();
			return;
		}
	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch){
		super.draw(batch);
		slot.draw(batch, 0, 0);
		if(weapon != null){
			font.draw(batch, weapon.name, x + 38, y + d_height - 26);
			String dmg = "" + Math.round(weapon.damage*10)/10f;
			String spd = "" + Math.round(weapon.speed*10)/10f;
			String knk = "" + Math.round(weapon.knockback*10)/10f;
			String wgt = "" + Math.round(weapon.weight*10)/10f;
			font.draw(batch, "Damage:\nSpeed:\nKnock:\nWeight:", x + 4, y + d_height - 50);
			font.draw(batch, dmg, TextHelper.alignRight(dmg, x + 160), y + d_height - 50);
			font.draw(batch, spd, TextHelper.alignRight(spd, x + 160), y + d_height - 66);
			font.draw(batch, knk, TextHelper.alignRight(knk, x + 160), y + d_height - 82);
			font.draw(batch, wgt, TextHelper.alignRight(wgt, x + 160), y + d_height - 98);
		}
	}

}
