package com.dungeon.game.entity.character;

import com.dungeon.game.effect.Immune;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechPopup;
import com.dungeon.game.world.World;

public abstract class Friend extends Character {
	
	protected int speechRadius;
	
	protected SpeechPopup speechBubble;
	
	public Dialogue dialogue;
	
	public Friend(World world, float x, float y) {
		super(world, x, y);
		
		speechBubble = new SpeechPopup(world,this);	
		
		addEffect(new Immune(world, -1));
	}
}
