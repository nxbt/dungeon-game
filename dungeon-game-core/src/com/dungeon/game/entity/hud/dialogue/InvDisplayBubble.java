package com.dungeon.game.entity.hud.dialogue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.world.World;

public class InvDisplayBubble extends SpeechBubble {
	
	protected Inventory inv;
	
	public InvDisplayBubble(World world, Character character, Inventory inv) {
		super(world, character);
		
		this.inv = inv;
		
		updateText("none");
		
		dWidth = 0;
		dHeight = 0;
		
		for(int i = 0; i < inv.slot.length; i++){
			inv.slot[i].x = 8+i%6*40;
			inv.slot[i].y = 8+(int)(i/6)*40;
			if(dWidth < inv.slot[i].x+40)dWidth = (int) (inv.slot[i].x+40);
			if(dHeight < inv.slot[i].y+40)dHeight = (int) (inv.slot[i].y+40);
		}
		
	}
	
	public void hovered(){
		inv.update();
		inv.graphic.x = x;
		inv.graphic.y = y;
		if(world.mouse.x > x&&world.mouse.x < x+dWidth&&world.mouse.y > y&&world.mouse.y < y+dHeight){
			inv.hovered();
		}
	}
	
	public void draw(SpriteBatch batch) {		
		SPEECH_BUBBLE.draw(batch, x, y, dWidth-dOffX, dHeight-dOffY);
		
		for(Slot s: inv.slot) {
			s.draw(batch, (int)x, (int)y);
		}
		
	}

}
