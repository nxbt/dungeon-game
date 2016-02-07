package com.dungeon.game.criteria;

import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.world.World;

public class Said extends Criteria{
	private Dialogue dialogue;
	private int index;
  
	public Said(World world, Dialogue dialogue, int index){
		super(world);
		this.dialogue = dialogue;
		this.index = index;
	}
  
	public boolean metCriteria(){
		for(SpeechBubble bubble: dialogue.speechBubbles){
			if(dialogue.potentialBubbles.indexOf(bubble) == index) return true;
		}
		
		return false;
	}
}
