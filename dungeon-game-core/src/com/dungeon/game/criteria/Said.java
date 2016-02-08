package com.dungeon.game.criteria;

import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.world.World;

public class Said extends Criteria{
	private Dialogue dialogue;
	private int index;
	boolean isTrue;
  
	public Said(World world, Dialogue dialogue, int index, boolean isTrue){
		super(world);
		this.dialogue = dialogue;
		this.index = index;
		this.isTrue = isTrue;
	}
  
	public boolean metCriteria(){
		for(SpeechBubble bubble: dialogue.speechBubbles){
			if(dialogue.potentialBubbles.indexOf(bubble) == index){
				if(isTrue)return true;
				else return false;
			}
		}
		
		if(isTrue)return false;
		else return true;
	}
}
