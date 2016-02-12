package com.dungeon.game.entity.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.HasGold;
import com.dungeon.game.criteria.True;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
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
		
		// need to complete reworkish to implement determinant choices. Breadnan ur way is cancer.
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"Greetings!", new Criteria[] {new True(world)}, new String[] {"second"}));
		dialogue.potentialBubbles.put("second", new SpeechBubble(world, this, "Goodbye!", new Criteria[] {new True(world)}, new String[] {"question"}));
//		dialogue.potentialBubbles.put("question", new SpeechChoice(world, 
//				new Criteria[]{new True(world), new True(world)}, 
//				new Criteria[][]{new Criteria[]{new True(world)},new Criteria[]{new True(world)}}, 
//				new String[][]{new String[]{"ONE"},new String[]{"TWO"}}, 
//				new Criteria[][]{new Criteria[]{new True(world)},new Criteria[]{new True(world)}}, 
//				new String[][]{new String[]{"ONE LONG"},new String[]{"TWO LONG"}}, 
//				new Criteria[][]{new Criteria[]{new True(world)},new Criteria[]{new True(world)}}, 
//				new String[][]{new String[]{"start"},new String[]{"second"}}
//				));
		
		dialogue.potentialBubbles.put("question", new SpeechChoice(world, 
				new String[]{"fuck1", "fuck2"}, 
				new String[]{"fuck", "fuckfuck"}, 
				new String[]{"start", "second"}));
		dialogue.potentialBubbles.put("rich?", new SpeechBubble(world, this, new Criteria[]{new HasGold(world, 30, world.player), new True(world)}, new String[]{"Your Rich!", "Poor guy..."}, "start"));
		
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
