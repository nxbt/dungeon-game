package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.True;
import com.dungeon.game.world.World;

public class SpeechChoice extends SpeechBubble implements Cloneable {
	private int[] yOffsets;
	private boolean madeChoice;
	private int choice;
	
	public Criteria[] choiceCriteria; //controls which choices are available
	public Criteria[][] choiceShortCriteria; //controls what the short text is for each choice 
	public String[][] choiceShorts; //contains short text for each choice
	public Criteria[][] choiceTextCriteria; //controls what the long text is for each choice
	public String[][] choiceTexts; //contains long text for each choice
	public Criteria[][] proceedKeyCriteria; //controls the proceed key
	public String[][] proceedKeys; //contains the proceed keys;
	
	public ArrayList<Integer> availableChoices;
	public ArrayList<String> availableChoiceShorts;

	public SpeechChoice(World world, Criteria[] choiceCriteria, Criteria[][] choiceShortCriteria, String[][] choiceShorts, Criteria[][] choiceTextCriteria, String[][] choiceTexts, Criteria[][] proceedKeyCriteria, String[][] proceedKeys){
		super(world, world.player);
		
		init(choiceCriteria, choiceShortCriteria, choiceShorts, choiceTextCriteria, choiceTexts, proceedKeyCriteria, proceedKeys);
	}
	
	public SpeechChoice(World world, String[] choiceShorts, String[] choiceTexts, String[] proceedKeys){
		super(world, world.player);

		Criteria[] choiceCriterias = new Criteria[choiceShorts.length];
		for(int i = 0; i < choiceCriterias.length; i++) choiceCriterias[i] = new True(world);

		Criteria[][] choiceShortCriterias = new Criteria[choiceShorts.length][1];
		for(int i = 0; i < choiceShortCriterias.length; i++) choiceShortCriterias[i][0] = new True(world);
		
		Criteria[][] choiceTextCriterias = new Criteria[choiceShorts.length][1];
		for(int i = 0; i < choiceTextCriterias.length; i++) choiceTextCriterias[i][0] = new True(world);
		
		Criteria[][] proceedKeyCriterias = new Criteria[choiceShorts.length][1];
		for(int i = 0; i < proceedKeyCriterias.length; i++) proceedKeyCriterias[i][0] = new True(world);
		
		String[][] choiceShorts2 = new String[choiceShorts.length][1];
		for(int i = 0; i < choiceShorts2.length; i++) choiceShorts2[i][0] = choiceShorts[i];
		
		String[][] choiceTexts2 = new String[choiceShorts.length][1];
		for(int i = 0; i < choiceTexts2.length; i++) choiceTexts2[i][0] = choiceTexts[i];
		
		String[][] proceedKeys2 = new String[choiceShorts.length][1];
		for(int i = 0; i < proceedKeys2.length; i++) proceedKeys2[i][0] = proceedKeys[i];
		
		init(choiceCriterias, choiceShortCriterias, choiceShorts2, choiceTextCriterias, choiceTexts2, proceedKeyCriterias, proceedKeys2);
	}

	private void init(Criteria[] choiceCriteria, Criteria[][] choiceShortCriteria, String[][] choiceShorts, Criteria[][] choiceTextCriteria, String[][] choiceTexts, Criteria[][] proceedKeyCriteria, String[][] proceedKeys){
		this.choiceCriteria = choiceCriteria;
		this.choiceShortCriteria = choiceShortCriteria;
		this.choiceShorts = choiceShorts;
		this.choiceTextCriteria = choiceTextCriteria;
		this.choiceTexts = choiceTexts;
		this.proceedKeyCriteria = proceedKeyCriteria;
		this.proceedKeys = proceedKeys;
		madeChoice = false;
		choice = 0;
		font = new BitmapFont(Gdx.files.internal("main_text.fnt"));
		Color temp = character.speechColor;
		temp.a = 0.7f;
		font.setColor(temp);
		font.getData().setScale(1f);
	}
	
	public void calc() {
		if(world.mouse.lb_pressed&&world.mouse.x>x&&world.mouse.x<x+d_width){
			if(world.mouse.y>y&&world.mouse.y<y+yOffsets[0]){
				choice = 0;
				madeChoice = true;
			}
			for(int i = 1; i < availableChoices.size(); i++){
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
	
	private void updateChoices() {
		availableChoices = new ArrayList<Integer>();
		for(int i = 0; i < choiceCriteria.length; i++){
			if(choiceCriteria[i].metCriteria()){
				availableChoices.add(i);
			}
		}
		Collections.reverse(availableChoices);
		yOffsets = new int[availableChoices.size()];

		int max_line_length = 0;
		

		d_height = 8;
		
		ArrayList<String> tempChoiceShorts = new ArrayList<String>();
		availableChoiceShorts = new ArrayList<String>();
		for(int i = 0; i < availableChoices.size(); i++){
			int index = availableChoices.get(i);
			for(int k = 0; k < choiceShortCriteria[index].length; k++){
				if(choiceShortCriteria[index][k].metCriteria()){
					tempChoiceShorts.add(choiceShorts[index][k]);
					break;
				}
			}
			tempChoiceShorts.set(i,availableChoices.size()-i+") "+ tempChoiceShorts.get(i));
			this.availableChoiceShorts.add("");
			ArrayList<String> lines = new ArrayList<String>(Arrays.asList(tempChoiceShorts.get(i).split("\\r?\\n")));
			
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
				
				availableChoiceShorts.set(i, availableChoiceShorts.get(i)+ lines.get(k) + "\n");
			}
			
			if(tempChoiceShorts.get(i).equals("")){
				availableChoiceShorts.set(i, "");
			}
			d_height += lines.size() * 16;
			yOffsets[i] = d_height;
		}
		

		d_width = max_line_length * 9 + 16;
		
	}
	
	public SpeechChoice clone() {
		SpeechChoice temp = (SpeechChoice) super.clone();
		temp.updateChoices();
		return (SpeechChoice) temp;
	}
	
	public void draw(SpriteBatch batch) {		
		SPEECH_BUBBLE.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
		for(int i = 0; i < availableChoices.size(); i++){
			font.draw(batch, availableChoiceShorts.get(i), x+8, y+yOffsets[i]-6);
		}
		
		
}

	public SpeechBubble getChoiceBubble() {
		SpeechBubble temp = new SpeechBubble(world, character, choiceTextCriteria[availableChoices.get(choice)], choiceTexts[availableChoices.get(choice)], proceedKeyCriteria[availableChoices.get(choice)], proceedKeys[availableChoices.get(choice)]);
		for(int i = 0; i < temp.textCriteria.length; i++){
			if(temp.textCriteria[i].metCriteria()){
				temp.updateText(temp.texts[i]);
				break;
			}
		}
		return temp;
	}
	

}