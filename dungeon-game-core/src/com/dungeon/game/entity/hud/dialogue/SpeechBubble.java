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
	
	public Criteria[] textCriteria; //controls which text is chosen
	public String[] texts; //different texts that can be spoken
	public Criteria[] keyCriteria; //controls which bubble is next in the conversation
	public String[] proceedKeys; //different keys for which bubble is to come next, based on the keyCriteria variable
	
	public boolean hasBeenSaid;
	
	private boolean began;
	
	public SpeechBubble(World world, Character character, Criteria[] textCriteria, String[] texts, Criteria[] keyCriteria, String[] proceedKeys) {
		super(world, 0, 0, Item.SIZE, Item.SIZE, "slot.png");
		
		init(character, textCriteria, texts, keyCriteria, proceedKeys);
	}
	
	public SpeechBubble(World world, Character character, String text, Criteria[] keyCriteria, String[] proceedKeys) {
		super(world, 0 ,0, Item.SIZE, Item.SIZE, "slot.png");
		
		init(character, new Criteria[]{new True(world)}, new String[]{text}, keyCriteria, proceedKeys);
	}
	
	public SpeechBubble(World world, Character character, Criteria[] textCriteria, String[] texts, String proceedKey) {
		super(world, 0 ,0, Item.SIZE, Item.SIZE, "slot.png");
		
		init(character, textCriteria, texts, new Criteria[]{new True(world)}, new String[]{proceedKey});
	}
	
	public SpeechBubble(World world, Character character, String text, String proceedKey){
		super(world,0,0, Item.SIZE, Item.SIZE, "slot.png");
		
		init(character, new Criteria[]{new True(world)}, new String[]{text}, new Criteria[]{new True(world)}, new String[]{proceedKey});
	}
	
	public SpeechBubble(World world, Character character, Criteria[] criteria, String[] texts, String[] proceedKeys) {
		super(world, 0, 0, Item.SIZE, Item.SIZE, "slot.png");
		
		init(character, criteria, texts, criteria, proceedKeys);
	}
	
	public SpeechBubble(World world, Character character){
		super(world, 0, 0, Item.SIZE, Item.SIZE, "slot.png");
		this.character = character;
	}

	private void init(Character character, Criteria[] textCriteria, String[] texts, Criteria[] keyCriteria, String[] proceedKeys) {	
		hasBeenSaid = false;
		
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		Color temp = character.speechColor;
		temp.a = 0.7f;
		font.setColor(temp);
		font.getData().setScale(1f);
		
		this.character = character;
		
		began = true;
		
		speechSpeed = 3;
		speechCounter = speechSpeed;
		
		this.textCriteria = textCriteria;
		this.texts = texts;
		this.keyCriteria = keyCriteria;
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
			int startIndex = 0;
			int charsCovered = 0;
			for(int k = 0; k < lines.get(i).length();k++){
				if(lines.get(i).charAt(k)!='\u200B'){
					charsCovered++;
					if(charsCovered == 50){
						startIndex = k;
						break;
					}
				}
			}
			if(charsCovered == 50) {
				for(int k = startIndex; k > 0; k--) {
					if(lines.get(i).charAt(k) == ' ') {
						lines.add(i+1,lines.get(i).substring(k+1));
						
						lines.set(i,lines.get(i).substring(0,k));
						
						break;
					}
				}
			}
			endText += lines.get(i) + "\n";
		}
		
		for( String line: lines){
			int length = 0;
			for(int k = 0; k < line.length(); k++){
				if(line.charAt(k)!='\u200B'){
					length++;
				}
			}
			max_line_length = Math.max(max_line_length, length);
		}
		
		if(text.equals("")){
			text = "";
			endText = "";
		}
		
		dHeight = lines.size() * 16 + 8;
		
		dWidth = max_line_length * 9 + 16;
	}
	
	public boolean done(){
		return text==null||text.equals(endText);
	}
	
	public String getProceedKey(){
		for(int i = 0; i < keyCriteria.length; i++){
			if(keyCriteria[i].metCriteria()) return proceedKeys[i];
		}
		return "start";
	}
	
	public SpeechBubble clone() {
		try {
			SpeechBubble temp = (SpeechBubble) super.clone();
			if(!(this instanceof SpeechChoice)){
				for(int i = 0; i < textCriteria.length; i++){
					if(textCriteria[i].metCriteria()){
						temp.updateText(texts[i]);
						break;
					}
				}
				
			}
			hasBeenSaid = true;
			return temp;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void post() {}
	
	public void draw(SpriteBatch batch) {		
			SPEECH_BUBBLE.draw(batch, x, y, dWidth-dOffX, dHeight-dOffY);
			font.draw(batch, text, x+8, y+dHeight-6);
			
	}

}
