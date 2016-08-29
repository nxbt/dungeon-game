package com.dungeon.game.entity.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.inventory.Shop;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class ShopGraphic extends InvGraphic {
	private static final Texture COIN = new Texture("coin.png");

	private BitmapFont font;

	public ShopGraphic(World world, Shop shop, float x, float y) {
		super(world,shop, x, y);
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.GOLD);
		
		dWidth = 0;
		dHeight = 14;
		this.inv = shop;
		for(Slot slot: inv.slot){
			if(slot.x + 100>dWidth) dWidth = (int) (slot.x + 100);
			if(slot.y + 50>dHeight) dHeight = (int) (slot.y + 50);
		}
	}

	protected void subDraw(SpriteBatch batch){
		for(int i = 0; i < inv.slot.length; i++) {
			Slot s = inv.slot[i];
			s.draw(batch, (int)x, (int)y);
			batch.draw(COIN, x+s.x+26,y+s.y);
			font.draw(batch, Integer.toString(((Shop)inv).costs[i]), (float) (20+x+s.x+Item.SIZE), 16+y+s.y+font.getScaleY()/2*12);
		}
	}
}
