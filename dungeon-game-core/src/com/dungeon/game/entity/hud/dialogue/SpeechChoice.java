package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.world.World;

public class SpeechChoice extends SpeechBubble implements Cloneable {
	private String[] choices;
	private String[] choiceText;
	private int[] proceedIndexes;
	private int[] yOffsets;
	private boolean madeChoice;
	private int choice;

	public SpeechChoice(World world, String[] choices, String[] choiceText, int[] proceedIndexes) {
		super(world, world.player, 0, "",0);
		madeChoice = false;
		this.choices = new String[choices.length];
		this.choiceText = choiceText;
		this.proceedIndexes = proceedIndexes;
		choice = 0;
		updateChoices(choices);
	}
	
	public void calc() {
		if(world.mouse.lb_pressed&&world.mouse.x>x&&world.mouse.x<x+d_width){
			if(world.mouse.y>y&&world.mouse.y<y+yOffsets[0]){
				choice = 0;
				madeChoice = true;
			}
			for(int i = 1; i < choices.length; i++){
				if(world.mouse.y>y+yOffsets[i-1]&&world.mouse.y<y+yOffsets[i]){
					choice = i;
					madeChoice = true;
				}
			}
		}
	}
	
	public boolean done(){
		return madeChoice;
	}
	
	private void updateChoices(String[] choices) {
		
		this.choices = new String[choices.length];
		yOffsets = new int[choices.length];

		int max_line_length = 0;
		

		d_height = 8;
		
		for(int i = 0; i < choices.length; i++){
			choices[i] = choices.length-i+") "+ choices[i];
			this.choices[i] = "";
			ArrayList<String> lines = new ArrayList<String>(Arrays.asList(choices[i].split("\\r?\\n")));
			
			for(int k = 0; k < lines.size(); k++) {
				if(lines.get(k).length() > 50) {
					for(int e = 50; e > 0; e--) {
						if(lines.get(k).charAt(e) == ' ') {
							lines.add(k+1,lines.get(k).substring(e+1));
							
							lines.set(k,lines.get(k).substring(0,e));
							
							break;
						}
					}
				}
				
				max_line_length = Math.max(max_line_length, lines.get(k).length());
				
				this.choices[i] += lines.get(k) + "\n";
			}
			
			if(choices[i].equals("")){
				this.choices[i] = "";
			}
			d_height += lines.size() * 16;
			yOffsets[i] = d_height;
		}
		

		d_width = max_line_length * 9 + 16;
		
	}
	
	public SpeechChoice clone() {
		return (SpeechChoice) super.clone();
	}
	
	public void draw(SpriteBatch batch) {		
		SPEECH_BUBBLE.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
		for(int i = 0; i < choices.length; i++){
			font.draw(batch, choices[i], x+8, y+yOffsets[i]-6);
		}
		
		
}

	public SpeechBubble getChoiceBubble() {
		return new SpeechBubble(world, character, 3, choiceText[choice], proceedIndexes[choice]);
	}
	

}