package com.dungeon.game.entity.hud;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class StatsWindow extends Window {
	
	private static final Texture VERT_PORTRAT = new Texture("vertPortrait.png");
	
	private BitmapFont font;
	
	private Character character;

	public StatsWindow(World world, Character character, float x, float y) {
		super(world, x, y);
		
		this.character = character;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(Color.WHITE);
		
		this.d_width = 230;
		this.d_height = 238;
	}
	
	public String fixDesc(String text) {
		String desctext = "";
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(text.split("\\r?\\n")));
		
		int max_line_length = 0;
		
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).length() > 25) {
				for(int k = 25; k > 0; k--) {
					if(lines.get(i).charAt(k) == ' ') {
						lines.add(i+1,lines.get(i).substring(k+1));
						
						lines.set(i,lines.get(i).substring(0,k));
						
						break;
					}
				}
			}
			
			max_line_length = Math.max(max_line_length, lines.get(i).length());
			
			desctext += lines.get(i) + "\n";
		}
		return desctext;
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub

	}
	
	public void subDraw(SpriteBatch batch) {
		batch.draw(VERT_PORTRAT, x+2, y+2);
		font.draw(batch, "Name: " + character.name, x+2, y+d_height - 14);
		font.draw(batch, fixDesc("Description: " + character.desc), x+2, y+d_height - 26);
		font.draw(batch, "Health:  "+ (int)Math.ceil(character.life) + "/" +  (int)Math.ceil(character.maxLife), x+78, y+d_height - 92);
		font.draw(batch, "Stanima: "+ (int)Math.ceil(character.life) + "/" +  (int)Math.ceil(character.maxLife), x+78, y+d_height - 104);
		font.draw(batch, "Mana:    "+ (int)Math.ceil(character.life) + "/" +  (int)Math.ceil(character.maxLife), x+78, y+d_height - 116);
	}

}
