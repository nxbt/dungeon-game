package com.dungeon.game.entity.hud;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class DescBox extends Hud {
	private final NinePatch DESC_BOX = new NinePatch(new Texture("desc_box.png"), 4, 4, 4, 4);
	
	private int screenWidth;
	private int screenHeight;
	
	public String text;
	
	BitmapFont desc;
	
	public DescBox(World world) {
		super(world, 0, 0, Item.SIZE, Item.SIZE, "slot.png");
		
		dOffX = 16;
		dOffY = 0;
		
		x = 30;
		y = 30;
		
		text = "";
		
		desc = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		desc.setColor(Color.LIGHT_GRAY);
		desc.setUseIntegerPositions(false);
	}

	@Override
	public void calc() {
		x = world.mouse.x;
		y = world.mouse.y;
		
		screenWidth = world.cam.width;
		screenHeight = world.cam.height;
		
		super.calc();
	}
	
	public void updateText(Item item) {
		updateText(item.name + "\n\n" + item.desc);
	}
	
	public void updateText(String text) {
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(text.split("\\r?\\n")));
		
		int max_line_length = 0;
		
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).length() > 30) {
				for(int k = 30; k > 0; k--) {
					if(lines.get(i).charAt(k) == ' ') {
						lines.add(i+1,lines.get(i).substring(k+1));
						
						lines.set(i,lines.get(i).substring(0,k));
						
						break;
					}
				}
			}
			
			max_line_length = Math.max(max_line_length, lines.get(i).length());
			
			this.text += lines.get(i) + "\n";
		}
		
		dHeight = lines.size() * 16 + 8;
		
		dWidth = max_line_length * 9 + 27;
	}
	
	public void draw(SpriteBatch batch) {
		if(!text.equals("")) {
			if(x + dWidth > screenWidth) x -= dWidth - 32;
			if(y + dHeight > screenHeight) y -= dHeight + 16;
			
			DESC_BOX.draw(batch, x, y, dWidth-dOffX, dHeight-dOffY);
			
			desc.draw(batch, text, x+6, y+dHeight-6);
		}
	}
	
	public void post() {}
}
