package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Friend;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.world.World;

public class SpeechPopup extends Hud {
	private final NinePatch SPEECH_POPUP = new NinePatch(new Texture("speechBubble.png"), 21, 4, 4, 21);
	
	public String text;
	
	public String endText;
	
	BitmapFont font;
	
	private Friend character;
	
	public boolean dismissed;
	
	private int speechSpeed;
	private int speechCounter;

	public SpeechPopup(World world, Friend character) {
		super(world, 0, 0);
		this.character = character;
		d_width = 200;
		d_height = 100;
		
		d_offx = 16;
		d_offy = 0;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		font.getData().setScale(1f);
		
		speechSpeed = 3;
		speechCounter = 0;
		text = "";
		endText = "";
		dismissed = false;
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
		
		d_height = lines.size() * 16 + 18;
		
		d_width = max_line_length * 9 + 38;
	}
	
	public void hovered(){
		if(!dismissed){
			if(world.mouse.rb_pressed){
				dismissed = true;
			}
			else if(world.mouse.lb_pressed) {
				if(character.dialogue != null) {
					character.dialogue.open();
					dismissed = true;
				}
			}
		}
	}
	
	public boolean done(){
		return text==null||text.equals(endText);
	}

	@Override
	public void post() {

	}
	
	public void setColor() {
		font.setColor(character.speechColor);
	}
	
	public void draw(SpriteBatch batch) {
		if(!dismissed&&!text.equals("")) {
//			if(x + d_width > bubbleWidth) x -= d_width - 32;
//			if(y + d_height > bubbleHeight) y -= d_height + 16;
			
			SPEECH_POPUP.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
			
			font.draw(batch, text, x+16, y+d_height-6);
			
//			text = "";
		}
	}
		

}
