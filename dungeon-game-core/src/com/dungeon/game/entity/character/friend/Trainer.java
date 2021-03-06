package com.dungeon.game.entity.character.friend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.item.equipable.Equipable;
import com.dungeon.game.light.Light;
import com.dungeon.game.world.World;

public class Trainer extends Friend {
	
	public Trainer(World world, float x, float y) {
		super(world, x, y, 32, 32, "trainer.png");
		
		sprite = new com.dungeon.game.textures.entity.Trainer().texture;
		
		speechColor = new Color(0.1f,0.5f,0.1f,1);
		speechBubble.setColor();	
		
		name = "Mentor";
		
		torq = 5;
		
		vision = 10;
		hearing = 3;
		
		speechRadius = 3;
		
		maxLife = 1;
		maxStam = 1;
		maxMana = 1;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 4f;
		fric = 0.4f;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		genVisBox();
		
		originX = 16;
		originY = 16;
		
		inv = new Inventory(world, new int[][] {}, 10, 100);
		
		equipSlots = new Slot[]{
				new Slot(world, new int[]{0, 0, 0}, inv),
				new Slot(world, new int[]{0, 0, 0}, inv)
		};
		
		equipItems = new Equipable[2];
		
		// \u200B to create pause;
		dialogue = new Dialogue(world, this);

		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"Hello!", "greetings"));
		dialogue.potentialBubbles.put("greetings", new SpeechBubble(world, this,"Are you here to learn how to stab things with maximum efficency?", "or..."));
		dialogue.potentialBubbles.put("or...", new SpeechBubble(world, this,"Or did you just blunder in here on accident?", "table"));
		dialogue.potentialBubbles.put("table", new SpeechBubble(world, this,"Anyhow. If you wish to consult me, come to the training table.", "end"));
	}

	@Override
	public void calc() {
		showPopupBubble("Hello. Here to learn the art of war?");
		
		if(seenEntities.contains(world.player)) {
			target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y, world.player.x-x));
			if(flipX) target_angle+=target_angle>0?-180:180;
		}
	}
	
	@Override
	public void post() {
		if(speechBubble.endText.equals("")){
			speechBubble.close();
		}else{
			if(!world.hudEntities.contains(speechBubble)&&!speechBubble.dismissed)speechBubble.open();	
			speechBubble.x = x-world.cam.x+world.cam.width/2;
			speechBubble.y = y-world.cam.y+world.cam.height/2;
		}
		
	}

}
