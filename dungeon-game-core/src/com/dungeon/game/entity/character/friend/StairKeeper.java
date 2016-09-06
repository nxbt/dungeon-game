package com.dungeon.game.entity.character.friend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.Invert;
import com.dungeon.game.criteria.Said;
import com.dungeon.game.criteria.True;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.InvBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Shop;
import com.dungeon.game.item.ammo.Arrow;
import com.dungeon.game.item.consumable.LifePotion;
import com.dungeon.game.item.equipable.weapon.Axe;
import com.dungeon.game.item.equipable.weapon.Sword;
import com.dungeon.game.light.Light;
import com.dungeon.game.textures.entity.Person;
import com.dungeon.game.world.World;

public class StairKeeper extends Friend {
	
	public StairKeeper(World world, float x, float y) {
		super(world, x, y, 32, 32, "mentor.png");
		sprite = new Person().texture;
		
		speechColor = new Color(0.3f,0.1f,0.4f,1);
		speechBubble.setColor();	
		
		name = "Stair Keeper";
		
		light = new Light(world, x, y, 1, 100, 0, this);
		
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
		genVisBox();
		
		originX = 16;
		originY = 16;

		Shop shop = new Shop(world, new int[][]{new int[]{0,10,10},new int[]{0,10,60},new int[]{0,10,110}},new int[]{10,200,20}, this, 10, 10);
		shop.slot[0].item = new Arrow(world);
		shop.slot[0].item.stack = 10;
		shop.slot[1].item = new Sword(world, 1);
		shop.slot[2].item = new LifePotion(world);
		shop.slot[2].item.stack = 10;
		
		// \u200B to create pause;
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this, new Criteria[] {new Invert(world, new Said(world, dialogue, "have answer")), new True(world)}, new String[] {"Hello... I am the keeper of the stairs...", "Hello again, traveler."}, new String[] {"below", "another question"}));
		dialogue.potentialBubbles.put("below", new SpeechBubble(world, this,"The stairs to the below.", "have you"));
	
		dialogue.potentialBubbles.put("have you", new SpeechBubble(world, this, "How much do you know about whats down there?", "have answer"));
//		
		dialogue.potentialBubbles.put("have answer", new SpeechChoice(world, 
				new String[]{"Nothing", "Enough","Quite a lot", "Too much"}, 
				new String[]{"I know nothing of this below...", "I've seen what I need to.", "I know much about the below.", "All too much, my friend... All too much."},
				new String[]{"know not", "know enough","know a lot", "know much"}));

		dialogue.potentialBubbles.put("know not", new SpeechBubble(world, this, "Then you are lucky that I am here. What would you like to know?", "help"));
		dialogue.potentialBubbles.put("know enough", new SpeechBubble(world, this, "That is good, but is there anything you'd like to ask?", "need help"));
		dialogue.potentialBubbles.put("know a lot", new SpeechBubble(world, this, "Then I shall let you on your way, adventurer.", "goodbye"));
		dialogue.potentialBubbles.put("know much", new SpeechBubble(world, this, "I see we are alike. Truely, you are prepared to brave the depths.", "goodbye"));
		
		dialogue.potentialBubbles.put("need help", new SpeechChoice(world, 
				new String[]{"Yes", "No"}, 
				new String[]{"Yes, I have some questions.", "No, I'm good."},
				new String[]{"help", "know a lot"}));
		
		dialogue.potentialBubbles.put("help", new SpeechChoice(world, 
				new String[]{"What do I need to know?", "What's down there?","What do I need down there?"}, 
				new String[]{"Is there anything that you believe I must know?", "What sort of evils lie below?", "What equipment should I bring with me?", "Would you say I am prepared to brave these depths?"},
				new String[]{"what know", "what down", "what need", "what prepared"}));

		dialogue.potentialBubbles.put("what know", new SpeechBubble(world, this, "Beneath this hatch is a dark and twisting sprawl of rooms and corridors we call the below. Monsterous beings lurk inside, waiting for hapless adventururers such as yourself to sate their terrible hunger.", "what know 2"));
		dialogue.potentialBubbles.put("what know 2", new SpeechBubble(world, this, "Legend says our ancestors built the below in search of an ancient power. But all they found were demons.", "what know 3"));
		dialogue.potentialBubbles.put("what know 3", new SpeechBubble(world, this, "These demons forced the humans all the way up here, and placed their monsters on the floors below to keep the humans away from them.", "what know 4"));
		dialogue.potentialBubbles.put("what know 4", new SpeechBubble(world, this, "The further down you go, and the closer to the demons you get, the stronger the monsters you encounter shall be.", "what know 5"));
		dialogue.potentialBubbles.put("what know 5", new SpeechBubble(world, this, "You will need to bring weapons and armor down with you, or even the weakest monsters will tear you to shreads.", "what know 6"));
		dialogue.potentialBubbles.put("what know 6", new SpeechBubble(world, this, new Criteria[] {new Invert(world, new Said(world, dialogue, "give axe")), new True(world)}, new String[] {"Here... take this. To protect yourself.", "I have already offered you what I can."}, new String[] {"give axe", "another question"}));
		
		dialogue.potentialBubbles.put("what down", new SpeechBubble(world, this, "Terrible monsters. And demons.", "what down 2"));
		dialogue.potentialBubbles.put("what down 2", new SpeechBubble(world, this, "Unspeakable horrors that will strike fear into the hearts of even the bravest of men.", "what down 3"));
		dialogue.potentialBubbles.put("what down 3", new SpeechBubble(world, this, "Be sure to bring weapons and armor with you, or you will be torn to shreds for sure.", "what know 6"));
		
		dialogue.potentialBubbles.put("what need", new SpeechBubble(world, this, "You'll need a weapon or two. And armor.", "what need 2"));
		dialogue.potentialBubbles.put("what need 2", new SpeechBubble(world, this, "Talk to the blacksmith for armor. For a bit of gold, I'm sure you can fit yourself well.", "what need 3"));
		dialogue.potentialBubbles.put("what need 3", new SpeechBubble(world, this, new Criteria[] {new Invert(world, new Said(world, dialogue, "give axe")), new True(world)}, new String[] {"And here's an axe. It's not a good weapon, but still better than nothing.", "And I have already offered you my axe."}, new String[] {"give axe", "another question"}));
		
		Inventory invent = new Inventory(world, new int[][]{new int[]{0,0,0}}, 0, 0, true);
		invent.slot[0].item = new Axe(world, 1);
		dialogue.potentialBubbles.put("give axe", new InvBubble(world, this, invent , "Thanks", "", "another question"));
		
		dialogue.potentialBubbles.put("another question", new SpeechBubble(world, this, "Do you have any other questions?", "need help"));
		
		dialogue.potentialBubbles.put("goodbye", new SpeechBubble(world, this, "If you have any questions, come to me to ask. I will remain here.", "end"));
	}

	@Override
	public void calc() {
		showPopupBubble("Don't be so reckless, come talk to me before you go.");
		
		if(seenEntities.contains(world.player)) {
			target_angle = (float) (180/Math.PI*Math.atan2(world.player.y-y, world.player.x-x));
			if(flipX) target_angle+=target_angle>0?-180:180;
		}
	}
	
	@Override
	public void post() {
		if(speechBubble.endText.equals("")){
			world.hudEntities.remove(speechBubble);
		}else{
			if(!world.hudEntities.contains(speechBubble))world.hudEntities.add(speechBubble);	
			speechBubble.x = x-world.cam.x+world.cam.width/2;
			speechBubble.y = y-world.cam.y+world.cam.height/2;
		}
		
	}

}
