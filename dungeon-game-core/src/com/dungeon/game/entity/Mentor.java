package com.dungeon.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.hud.SpeechPopup;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Mentor extends Friend {
	
	private SpeechPopup speechBubble; 
	public Mentor(World world, float x, float y) {
		super(world, x, y);
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
		
		d_offx = 0;
		d_offy = 0;
		
		sprite = new Texture("mentor.png");
		angle = 0;
		
		speechBubble = new SpeechPopup(world,this);	
	}

	@Override
	public void init() {

		
	}

	@Override
	public void calc() {
		if(seenEntities.contains(world.player)){
			target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y, world.player.x-x));
			if(!world.player.fightMode&&speechBubble.endText.equals("")){
				speechBubble.updateText("FUCK YOU BITCH!");
				speechBubble.dismissed = false;
			}
			
		}else speechBubble.updateText("");
		if(world.player.fightMode)speechBubble.updateText("");
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
