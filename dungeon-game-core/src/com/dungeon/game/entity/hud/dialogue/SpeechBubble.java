package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.True;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.world.World;

public class SpeechBubble extends Hud implements Cloneable {
	
	protected final NinePatch SPEECH_BUBBLE = new NinePatch(new Texture("hudSpeechBubble.png"), 8, 8, 8, 8);
	
	public Character character;

	public String text;
	
	public String endText;
	
	protected BitmapFont font;
	
	private int speechSpeed;
	private int speechCounter;
	public Criteria[] textCriterias;
	public String[] texts;
	public Criteria[] keyCriterias;
	public String[] proceedKeys;
	
	private boolean began;
	
	public SpeechBubble(World world, Character character, Criteria[] textCriterias, String[] texts, Criteria[] keyCriterias, String[] proceedKeys) {
		super(world, 0, 0);
		
		init(world, character, textCriterias, texts, keyCriterias, proceedKeys);
	}
	
	public SpeechBubble(World world, Character character, String text, Criteria[] keyCriterias, String[] proceedKeys) {
		super(world, 0 ,0);
		
		init(world, character, new Criteria[]{new True(world)}, new String[]{text}, keyCriterias, proceedKeys);
	}
	
	public SpeechBubble(World world, Character character, Criteria[] textCriterias, String[] texts, String proceedKey) {
		super(world, 0 ,0);
		
		init(world, character, textCriterias, texts, new Criteria[]{new True(world)}, new String[]{proceedKey});
	}
	
	public SpeechBubble(World world, Character character, String text, String proceedKey){
		super(world,0,0);
		
		init(world, character, new Criteria[]{new True(world)}, new String[]{text}, new Criteria[]{new True(world)}, new String[]{proceedKey});
	}

	public void init(World world, Character character, Criteria[] textCriterias, String[] texts, Criteria[] keyCriterias, String[] proceedKeys) {	
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		Color temp = character.speechColor;
		temp.a = 0.7f;
		font.setColor(temp);
		font.getData().setScale(1f);
		
		this.character = character;
		
		began = true;
		
		speechSpeed = 3;
		speechCounter = speechSpeed;
		
		this.textCriterias = textCriterias;
		this.texts = texts;
		this.keyCriterias = keyCriterias;
		this.proceedKeys = proceedKeys;
	}
	
	@Override
	public void calc() {
		if(world.mouse.lb_pressed && !began) {
			text = endText;
		}
		if(speechCounter == 0){
			int textLength = text.length();
			int endTextLength = endText.length();
			if(textLength!=endTextLength){
				text+=endText.charAt(textLength);
			}
			speechCounter = speechSpeed;
			
			char lastChar = text.charAt(text.length()-1);
			
			if(lastChar == ' '||lastChar == '\'') speechCounter = 0;
			else if(lastChar == ',') speechCounter*=3;
			else if(lastChar == '.' || lastChar == '!' || lastChar == '?' || lastChar == ';') speechCounter*=5;
			else if(lastChar == '\u200B') speechCounter*=2;
			else if(lastChar == '-') speechCounter*=10;
			else if(lastChar == ':') speechCounter*=10;
			
		}else speechCounter--;
		
		if(began) began = false;
	}
	
	public void updateText(String text) {
		this.endText = "";
		this.text = "";
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(text.split("\\r?\\n")));
		
		int max_line_length = 0;
		
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).length() > 50) {
				for(int k = 50; k > 0; k--) {
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
	
	public String getProceedKey(){
		for(int i = 0; i < keyCriterias.length; i++){
			if(keyCriterias[i].metCriteria())return proceedKeys[i];
		}
		return "start";
	}
	
	public SpeechBubble clone() {
		try {
			SpeechBubble temp = (SpeechBubble) super.clone();
			for(int i = 0; i < textCriterias.length; i++){
				if(textCriterias[i].metCriteria()){
					temp.updateText(texts[i]);
					break;
				}
			}
			return temp;
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
