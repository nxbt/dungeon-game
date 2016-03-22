package com.dungeon.game.entity.hud.dialogue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.world.World;

public class InvDisplayBubble extends SpeechBubble {
	
	private Inventory inv;

	public InvDisplayBubble(World world, Character character, Inventory inv) {
		super(world, character);
		this.inv = inv;
		updateText("none");
		d_width = 0;
		d_height = 0;
		for(int i = 0; i < inv.slot.length; i++){
			inv.slot[i].x = 8+i%5*40;
			inv.slot[i].y = 8+(int)(i/5)*40;
			if(d_width < inv.slot[i].x+40)d_width = (int) (inv.slot[i].x+40);
			if(d_height < inv.slot[i].y+40)d_height = (int) (inv.slot[i].y+40);
		}
	}
	
	public void update(){
		inv.update();
		inv.graphic.x = x;
		inv.graphic.y = y;
		if(world.mouse.x > x&&world.mouse.x < x+d_width&&world.mouse.y > y&&world.mouse.y < y+d_height){
			inv.hovered();
		}
	}
	
	public void draw(SpriteBatch batch) {		
		SPEECH_BUBBLE.draw(batch, x, y, d_width-d_offx, d_height-d_offy);
		
		for(Slot s: inv.slot) {
			s.draw(batch, (int)x, (int)y);
		}
		
	}

}
