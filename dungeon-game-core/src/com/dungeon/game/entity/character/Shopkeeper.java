package com.dungeon.game.entity.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.InvBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
import com.dungeon.game.inventory.Shop;
import com.dungeon.game.item.consumable.LifePotion;
import com.dungeon.game.item.weapon.Sword;
import com.dungeon.game.item.weapon.ammo.Arrow;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Shopkeeper extends Friend {
	
	public Shopkeeper(World world, float x, float y) {
		super(world, x, y, 32, 32, "shopkeeper.png");
		
		speechColor = new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1);
		speechBubble.setColor();
		
		name = "Shopkeeper";
		
		torq = 10f;
		
		vision = 10;
		hearing = 3;
		
		speechRadius = 2;
		
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

		Shop shop = new Shop(world, new int[][]{new int[]{0,10,10},new int[]{0,10,60},new int[]{0,10,110}},new int[]{10,200,20}, this, 10, 10);
		shop.slot[0].item = new Arrow(world);
		shop.slot[0].item.stack = 10;
		shop.slot[1].item = new Sword(world, 10, 10);
		shop.slot[2].item = new LifePotion(world);
		shop.slot[2].item.stack = 10;
		
		// \u200B to create pause;
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"Something you were looking for?", "want item"));
	
		dialogue.potentialBubbles.put("want item", new SpeechChoice(world, 
				new String[]{"Yes", "No", "What do you have?"}, 
				new String[]{"Actually, yes.", "Just looking around.", "Depends- what do you have?"},
				new String[]{"shop", "talk","interest"}));
		
		dialogue.potentialBubbles.put("interest", new SpeechBubble(world, this, "Take a look.", "shop"));
		
		dialogue.potentialBubbles.put("talk", new SpeechBubble(world, this, "Talk to me if anything interests you.", "end"));
		
		dialogue.potentialBubbles.put("shop", new InvBubble(world, this, shop, "goodbye"));
		
		dialogue.potentialBubbles.put("goodbye", new SpeechBubble(world, this, "Come again.", "end"));
	}

	@Override
	public void calc() {
		if(seenEntities.contains(world.player)){
			target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y, world.player.x-x));
			if(flipX)target_angle+=target_angle>0?-180:180;
			if(!world.player.fightMode&&speechBubble.endText.equals("")){
				if(!world.hudEntities.contains(dialogue))speechBubble.updateText("Everything's for sale!");
				speechBubble.dismissed = false;
			}
			
		}else{
			target_angle = angle;
			
			if(world.hudEntities.contains(dialogue)) {
				speechBubble.updateText("");
				dialogue.close();
			}
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
