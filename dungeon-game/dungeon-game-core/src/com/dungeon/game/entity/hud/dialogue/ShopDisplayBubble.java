package com.dungeon.game.entity.hud.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.inventory.Shop;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class ShopDisplayBubble extends InvDisplayBubble{
	private static final Texture COIN = new Texture("coin.png");

	private BitmapFont font;
	
	public ShopDisplayBubble(World world, Character character, Shop shop) {
		super(world, character, shop);
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.GOLD);
		
		for(int i = 0; i < inv.slot.length; i++){
			inv.slot[i].x = 8+i%3*80;
			inv.slot[i].y = 8+(int)(i/3)*40;
			if(d_width < inv.slot[i].x+40)d_width = (int) (inv.slot[i].x+80);
			if(d_height < inv.slot[i].y+40)d_height = (int) (inv.slot[i].y+40);
		}
	}
	
	public void draw(SpriteBatch batch) {		
		SPEECH_BUBBLE.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
		
		for(int i = 0; i < inv.slot.length; i++) {
			inv.slot[i].draw(batch, (int)x, (int)y);
			batch.draw(COIN, x+inv.slot[i].x+26,y+inv.slot[i].y);
			font.draw(batch, Integer.toString(((Shop)inv).costs[i]), (float) (20+x+inv.slot[i].x+Item.SIZE), 16+y+inv.slot[i].y+font.getScaleY()/2*12);
		}
		
	}
}
