package com.dungeon.game.entity.hud;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.Camera;
import com.dungeon.game.world.World;

public class DescBox extends Hud {
	private final NinePatch DESC_BOX = new NinePatch(new Texture("desc_box.png"), 4, 4, 4, 4);
	
	private int screenWidth;
	private int screenHeight;
	
	public String text;
	
	BitmapFont desc;
	
	public DescBox() {
		super(0, 0);
	}

	@Override
	public void init() {
		d_width = 200;
		d_height = 100;
		
		d_offx = 16;
		d_offy = 0;
		
		x = 30;
		y = 30;
		
		text = "";
		
		desc = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		desc.setColor(Color.LIGHT_GRAY);
		desc.getData().setScale(1f);
	}

	@Override
	public void calc(World world) {
		x = world.mouse.x;
		y = world.mouse.y;
		
		screenWidth = world.cam.WIDTH;
		screenHeight = world.cam.HEIGHT;
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
		
		d_height = lines.size() * 16 + 8;
		
		d_width = max_line_length * 9 + 27;
	}
	
	public void draw(SpriteBatch batch) {
		if(!text.equals("")) {
			if(x + d_width > screenWidth) x -= d_width - 32;
			if(y + d_height > screenHeight) y -= d_height + 16;
			
			DESC_BOX.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
			
			desc.draw(batch, text, x+6, y+d_height-6);
			
			text = "";
		}
	}
}
