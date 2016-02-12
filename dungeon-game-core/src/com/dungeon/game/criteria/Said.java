package com.dungeon.game.criteria;

import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.world.World;

public class Said extends Criteria{
	private Dialogue dialogue;
	private String key;
  
	public Said(World world, Dialogue dialogue, String key){
		super(world);
		this.dialogue = dialogue;
		this.key = key;
	}
  
	public boolean metCriteria(){
		for(SpeechBubble bubble: dialogue.speechBubbles){
			if(dialogue.potentialBubbles.get(key).equals(bubble)) return true;
		}
		
		return false;
	}
}
