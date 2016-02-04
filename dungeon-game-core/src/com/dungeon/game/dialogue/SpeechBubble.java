package com.dungeon.game.dialogue;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.world.World;
import com.dungeon.game.entity.Character;

public class SpeechBubble extends Hud {
	
	private final NinePatch SPEECH_BUBBLE = new NinePatch(new Texture("hudSpeechBubble.png"), 8, 8, 8, 8);
	
	public Character character;

	public String text;
	
	public String endText;
	
	BitmapFont font;
	
	private int speechSpeed;
	private int speechCounter;

	public SpeechBubble(World world, Character character, int speed, String text) {
		super(world, 0, 0);
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(0,0,0,1);
		font.getData().setScale(1f);
		
		this.character = character;
		
		speechSpeed = speed;
		speechCounter = speechSpeed;
		
		updateText(text);
	}

	@Override
	public void calc() {
		if(speechCounter == 0){
			int textLength = text.length();
			int endTextLength = endText.length();
			if(textLength!=endTextLength){
				text+=endText.charAt(textLength);
			}
			speechCounter = speechSpeed;
		}else speechCounter--;
		
	}
	
	public void updateText(String text) {
		this.endText = "";
		this.text = "";
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(text.split("\\r?\\n")));
		
		int max_line_length = 0;
		
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).length() > 20) {
				for(int k = 20; k > 0; k--) {
					if(lines.get(i).charAt(k) == ' ') {
						lines.add(i+1,lines.get(i).substring(k+1));
						
						lines.set(i,lines.get(i).substring(0,k));
						
						break;
					}
				}
			}
			
			max_line_length = Math.max(max_line_length, lines.get(i).length());
			
			endText += lines.get(i) + "\n";
		}
		
		if(text.equals("")){
			text = "";
			endText = "";
		}
		
		d_height = lines.size() * 16 + 8;
		
		d_width = max_line_length * 9 + 16;
	}
	
	public boolean done(){
		return text==null||text.equals(endText);
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(SpriteBatch batch) {
//			if(x + d_width > bubbleWidth) x -= d_width - 32;
//			if(y + d_height > bubbleHeight) y -= d_height + 16;
			
			SPEECH_BUBBLE.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
			
			font.draw(batch, text, x+8, y+d_height-6);
			
	}

}
