package com.dungeon.game.entity.character.friend;

import com.dungeon.game.effect.Immune;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechPopup;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.world.World;

public abstract class Friend extends Character {
	
	protected int speechRadius;
	
	protected SpeechPopup speechBubble;
	
	public Dialogue dialogue;
	
	public Inventory sharedInventory;
	
	public Friend(World world, float x, float y, int width, int height, String filename) {
		super(world, x, y, width, height, filename);
		
		speechBubble = new SpeechPopup(world,this);	
		
		addEffect(new Immune(world, -1));
	}
	
	public void hovered() {
		if(world.mouse.lb_pressed) {
			dialogue.open();
			world.player.focusedEntity = this;
			speechBubble.dismissed = true;
		}
	}
}
