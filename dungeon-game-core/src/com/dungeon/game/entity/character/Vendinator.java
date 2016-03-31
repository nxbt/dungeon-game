package com.dungeon.game.entity.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.InvBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
import com.dungeon.game.inventory.Shop;
import com.dungeon.game.item.Arrow;
import com.dungeon.game.item.LifePotion;
import com.dungeon.game.item.ManaPotion;
import com.dungeon.game.item.Stick;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Vendinator extends Friend {

	public Vendinator(World world, float x, float y) {
		super(world, x, y, 32, 32, "vendinator.png");
		speechColor = new Color(0.1f,0.1f,0.5f,1);
		speechBubble.setColor();	
		
		name = "Vendinator";

		face = new Texture("vendinator_happy.png");
		
		torq = 0;
		
		vision = 10;
		hearing = 3;
		
		speechRadius = 3;
		
		maxLife = 1;
		maxStam = 1;
		maxMana = 1;
		
		life = maxLife;
		stam = maxStam;
		mana = maxMana;
		
		acel = 0;
		mvel = 0;
		fric = 0;
		
		hitbox = new Polygon(new float[]{2,2,30,2,30,30,2,30});
		
		origin_x = 16;
		origin_y = 16;
		
		Shop shop = new Shop(world, new int[][]{new int[]{0,10,10},new int[]{0,10,10},new int[]{0,10,60},new int[]{0,10,110}},new int[]{5,30,15,5}, this, 10, 10);
		shop.slot[0].item = new Arrow(world);
		shop.slot[0].item.stack = 12;
		shop.slot[1].item = new LifePotion(world);
		shop.slot[1].item.stack = 10;
		shop.slot[2].item = new ManaPotion(world);
		shop.slot[2].item.stack = 10;
		shop.slot[3].item = new Stick(world);
		shop.slot[3].item.stack = 10;
		
		// \u200B to create pause;
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"G\u200BR\u200BE\u200BE\u200BT\u200BI\u200BN\u200BG\u200BS.\u200B\u200B\u200B\u200B I\u200B\u200B AM\u200B\u200B VENDINATOR!", "greetings response"));
		
		dialogue.potentialBubbles.put("greetings response", new SpeechChoice(world, 
				new String[]{"Hello?", "Are you a machine?", "Are you selling something?", "Cool robot."}, 
				new String[]{"Umm... Hello?", "Wait, are you a machine?", "So... are you selling something?", "Wow, what a cool robot."},
				new String[]{"pitch", "pitch", "pitch", "pitch"}));
		
		dialogue.potentialBubbles.put("pitch", new SpeechBubble(world, this,"B\u200B\u200BU\u200B\u200BY\u200B\u200B\u200B\u200B\u200B\u200B, HUMAN.", "pitch response"));
		
		dialogue.potentialBubbles.put("pitch response", new SpeechChoice(world, 
				new String[]{"Okay.", "What do you have?", "No thanks.", "No way."}, 
				new String[]{"Umm... okay.", "Well what do you have?", "Yeah... no thanks.", "No way, I'm outta here."},
				new String[]{"shop", "shop", "dismiss", "goodbye"}));
		
		dialogue.potentialBubbles.put("shop", new InvBubble(world, this, shop, "goodbye"));
		
		dialogue.potentialBubbles.put("dismiss", new SpeechBubble(world,this, "THEN...\u200B\u200B\u200B\u200B\u200B\u200B LEAVE.", "end"));
		dialogue.potentialBubbles.put("goodbye", new SpeechBubble(world,this, "GOODBYE!", "end"));
		
		
		dialogue.begin();
	}

	public void hovered() {
		if(world.mouse.lb_pressed) {
			dialogue.open();
			speechBubble.dismissed = true;
		}
	}
	
	public void calc() {
		if(seenEntities.contains(world.player)){
			target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y, world.player.x-x));
			if(!world.player.fightMode&&speechBubble.endText.equals("")){
				if(!world.hudEntities.contains(dialogue))speechBubble.updateText("BUY!\u200B\u200B\u200B\u200B\u200B\u200B BUY!\u200B\u200B\u200B\u200B\u200B\u200B BUY!");
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
