package com.dungeon.game.entity.hud;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.effect.Effect;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class DescWindow extends Window {
	private String text;
	
	public DescWindow(World world, float x, float y) {
		super(world, x, y);
	}

	@Override
	public void post() {
		
	}

	public void subDraw(SpriteBatch batch) {
		font.draw(batch, text, x+6, y+d_height-16+scroll);
	}
	
	public void updateText(Item item) {
		updateText("Item: " + item.name + "\n\n" + item.getDesc());
	}
	
	public void updateText(Effect effect) {
		updateText("Effect: " + effect.name + "\n\n" + effect.getDesc());
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
		}
		
		d_height = lines.size() * 16 + 18;
		
		d_width = max_line_length * 9 + 27;
		
		if(d_height > 100) d_height = 200;
	}
	
}
