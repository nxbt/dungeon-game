package com.dungeon.game.entity.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.*;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
import com.dungeon.game.item.Sword;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

//import com.dungeon.game.fuck.you.bitch;

public class Mentor extends Friend {
	
	public Mentor(World world, float x, float y) {
		super(world, x, y);
		
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
		
		acel = 1.5f;
		mvel = 5;
		fric = 0.5f;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		
		origin_x = 16;
		origin_y = 16;
		
		d_width = 32;
		d_height = 32;
		
		// \u200B to create pause;
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"Hey there.", "want sword question"));
		
		dialogue.potentialBubbles.put("want sword question", new SpeechBubble(world, this, "Do you want a sword? You'll need a weapon to fight off the monsters. There's quite a few around here...", "want sword answer"));
		
		dialogue.potentialBubbles.put("want sword answer", new SpeechChoice(world, 
				new String[]{"Yes.", "I have a sword."}, 
				new String[]{"Yeah, I'll take a sword.", "I already have a sword."},
				new String[]{"check no sword", "check sword"}));
		
		dialogue.potentialBubbles.put("lie defence", new SpeechChoice(world, 
				new String[]{"What's it to you?", "What if I wanted two?", "Didn't know you cared."}, 
				new String[]{"What's it to you?", "What if I wanted two? Maybe I wanted to dual wield...", "Sorry, didn't know you cared that I already had one... My bad."},
				new String[]{"refuse sword", "check two swords", "reasure"}));
		
		dialogue.potentialBubbles.put("check two swords", new SpeechBubble(world, this, 
				new Criteria[]{new HasItem(world, new Sword(world, 0, 0), world.player, 2), new True(world)},
				new String[]{"Bu- ... Wha- ... I can't even believe you. You clearly have two swords... What are you even doing? Besides...", "Even if that was the case..."},
				"refuse sword"));
		
		dialogue.potentialBubbles.put("reasure", new SpeechBubble(world, this, "It's okay.", "refuse sword"));
		
		dialogue.potentialBubbles.put("refuse sword", new SpeechBubble(world, this, "I'm trying to help people who are unarmed. But you're fine, I'm not going to give you anything.", "goodbye"));
		
		dialogue.potentialBubbles.put("check no sword", new SpeechBubble(world, this, 
				new Criteria[]{new Invert(world, new HasItem(world, new Sword(world, 0, 0), world.player)), new True(world)},
				new String[]{"No problem.", "Wait! you already have a sword! What are you tryin' to pull?"},
				new String[]{"give sword", "lie defence"}));
		
		dialogue.potentialBubbles.put("check sword", new SpeechBubble(world, this,
				new Criteria[]{new HasItem(world, new Sword(world, 0, 0), world.player), new True(world)},
				new String[]{"Oh, alright.", "Um... no you don't."}, 
				new String[]{"goodbye", "give sword"}));
		
		dialogue.potentialBubbles.put("give sword", new SpeechBubble(world, this, "Here ya go!", "goodbye"));
		
		dialogue.potentialBubbles.put("goodbye", new SpeechBubble(world, this, "See ya!", new Criteria[] {new True(world)}, new String[] {"start"}));
		
		dialogue.begin();
		
		sprite = new Texture("mentor.png");
	}

	@Override
	public void calc() {
		if(seenEntities.contains(world.player)){
			target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y, world.player.x-x));
			if(!world.player.fightMode&&speechBubble.endText.equals("")){
				if(!world.hudEntities.contains(dialogue))speechBubble.updateText("I am here to, well, help.");
				speechBubble.dismissed = false;
			}
			
		}else{
			speechBubble.updateText("");
			dialogue.close();
		}
		if(world.player.fightMode||Math.sqrt((x-world.player.x)*(x-world.player.x)+(y-world.player.y)*(y-world.player.y))>speechRadius*Tile.TS)speechBubble.updateText("");
	}

	@Override
	public void post() {
		if(speechBubble.endText.equals("")){
			world.hudEntities.remove(speechBubble);
		}else{
			if(!world.hudEntities.contains(speechBubble))world.hudEntities.add(speechBubble);	
			speechBubble.x = x-world.cam.x+world.cam.WIDTH/2;
			speechBubble.y = y-world.cam.y+world.cam.HEIGHT/2;
		}
		
	}

}
