package com.dungeon.game.entity.hud.window;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class DescWindow extends Window {
	private String text;
	
	private String drawText;
	
	public DescWindow(World world, float x, float y) {
		super(world, x, y);
	}

	@Override
	public void post() {
		String[] textLines = text.split("\n");
		
		if(scroll > textLines.length-14) scroll = textLines.length-14;
		if(scroll < 0) scroll = 0;
		
		drawText = "";
		
		for(int i = 0; i < Math.min(textLines.length-scroll, 14); i++) {
			drawText += textLines[(int) scroll+i] + "\n";
		}
	}

	public void draw(SpriteBatch batch) {
		super.draw(batch);
		font.draw(batch, drawText, x+6, y+dHeight-16);
	}
	
	public void updateText(Item item) {
		updateText(item.getDesc());
	}
	
	public void updateText(Effect effect) {
		updateText(effect.getDesc());
	}
	
	public void updateText(String text) {
		this.text = "";
		
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
			
			contentHeight = lines.size() * 16;
		}
		
		dHeight = lines.size() * 16 + 18;
		
		dWidth = max_line_length * 9 + 27;
		
		if(dHeight > 100) dHeight = 240;
	}
	
}
