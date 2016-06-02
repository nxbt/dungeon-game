package com.dungeon.game.criteria;

import com.dungeon.game.entity.hud.dialogue.Dialogue;
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
		return dialogue.potentialBubbles.get(key).hasBeenSaid;
	}
}