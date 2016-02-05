package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class SpeechBubble extends Hud implements Cloneable {
	
	protected final NinePatch SPEECH_BUBBLE = new NinePatch(new Texture("hudSpeechBubble.png"), 8, 8, 8, 8);
	
	public Character character;

	public String text;
	
	public String endText;
	
	protected BitmapFont font;
	
	private int speechSpeed;
	private int speechCounter;

	public int proceedIndex;

	public SpeechBubble(World world, Character character, int speed, String text, int proceedIndex) {
		super(world, 0, 0);
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.setColor(character.speechColor);
		font.getData().setScale(1f);
		
		this.character = character;
		
		speechSpeed = speed;
		speechCounter = speechSpeed;
		this.proceedIndex = proceedIndex;
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
	
	public SpeechBubble clone() {
		try {
			return (SpeechBubble) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch) {		
			SPEECH_BUBBLE.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
			
			font.draw(batch, text, x+8, y+d_height-6);
			
	}

}
