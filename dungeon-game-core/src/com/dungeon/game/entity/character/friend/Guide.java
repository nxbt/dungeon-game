package com.dungeon.game.entity.character.friend;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.HasItem;
import com.dungeon.game.criteria.Invert;
import com.dungeon.game.criteria.Said;
import com.dungeon.game.criteria.True;
import com.dungeon.game.entity.Entity;
import com.dungeon.game.entity.character.enemy.TutorialGoon;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.InvBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
import com.dungeon.game.entity.particle.Poof;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.item.Key;
import com.dungeon.game.item.consumable.LifePotion;
import com.dungeon.game.item.equipable.Lantern;
import com.dungeon.game.item.equipable.weapon.Sword;
import com.dungeon.game.light.Light;
import com.dungeon.game.textures.entity.Person;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

public class Guide extends Friend {
	
	private int stage;
	
	private ArrayList<Dialogue> dialogues;
	
	public Guide(World world, float x, float y) {
		super(world, x, y, 32, 32, "mentor.png");
		sprite = new Person().texture;
		
		speechColor = new Color(0.1f,0.5f,0.1f,1);
		speechBubble.setColor();	
		
		name = "Mentor";
		
		light = new Light(world, x, y, 20, 100, 0, this);
		
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

		dialogues = new ArrayList<Dialogue>();
		
		// \u200B to create pause;
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this, new Criteria[] {new Invert(world, new Said(world, dialogue, "over")), new True(world)}, new String[] {"Greetings. Click anywhere to continue.", "Need some help?"}, new String[] {"why here", "open door"}));
		
		dialogue.potentialBubbles.put("why here", new SpeechBubble(world, this,"You are here to learn the ways of an adventurer, no?", "how to answer"));
		
		dialogue.potentialBubbles.put("how to answer", new SpeechBubble(world, this,"Oh- I almost forgot; click on the dialogue option you wish to select.", "answer why here"));
		
		dialogue.potentialBubbles.put("answer why here", new SpeechChoice(world, 
		new String[]{"Yes.", "Maybe?","No", "I don't know"}, 
		new String[]{"Yes, I am.", "Uh... maybe?", "Umm... no.", "Actually, I don't know."},
		new String[]{"why yes", "why maybe","why no", "why idk"}));
		
		dialogue.potentialBubbles.put("why yes", new SpeechBubble(world, this,"Good. Then I shall instruct you, future adventurer.", "how to move"));
		
		dialogue.potentialBubbles.put("why maybe", new SpeechBubble(world, this,"Hmm... well, no matter. I shall instruct you in any case, future adventurer.", "how to move"));
		
		dialogue.potentialBubbles.put("why no", new SpeechBubble(world, this,"Umm... Yes you are. Why else would you have come here?", "how to move"));
		
		dialogue.potentialBubbles.put("why idk", new SpeechBubble(world, this,"Well that's not a good sign. Then I will say... yes. I shall instruct you, future adventurer.", "how to move"));
		
		dialogue.potentialBubbles.put("how to move", new SpeechBubble(world, this,"An adventurer uses the keys W, A, S, and D to move and opens their inventory with E.", "locked door"));
		
		dialogue.potentialBubbles.put("locked door", new SpeechBubble(world, this,"Hmm... it would appear that the only way out of this room is through a locked door. ", "how open"));
		
		dialogue.potentialBubbles.put("how open", new SpeechBubble(world, this,"How would you open a locked door?", "how open answer"));
		
		dialogue.potentialBubbles.put("how open answer", new SpeechChoice(world, 
				new String[]{"Pick it.", "Break it down.", "Combat.", "With a key?"}, 
				new String[]{"I would pick it with a lockpick.", "I would break the door down with brute strength.", "I shall engage the door in combat.", "With a key?"},
				new String[]{"pick", "break","combat", "key"}));
		
		dialogue.potentialBubbles.put("pick", new SpeechBubble(world, this,"That's a good idea. Uh... here, use this 'lockpick.' Click on it to add it to your inventory.", "give key"));
		
		dialogue.potentialBubbles.put("break", new SpeechBubble(world, this,"Good luck with that. This door, and pretty much every door you'll find, is incredibly tough. Here. Try this key. Click on it to add it to your inventory.", "give key"));
		
		dialogue.potentialBubbles.put("combat", new SpeechBubble(world, this,"Right. Uhh... not this one. Just... use this key. Click on it to add it to your inventory.", "give key"));
		
		dialogue.potentialBubbles.put("key", new SpeechBubble(world, this,"Ding ding ding! Wow! You're the first person to get that right! Here, take this one. Click on it to add it to your inventory.", "give key"));
		
		Inventory invent = new Inventory(world, new int[][]{new int[]{0,0,0}}, 0, 0, true);
		invent.slot[0].item = new Key(world);
		dialogue.potentialBubbles.put("give key", new InvBubble(world, this, invent , "check key"));
		
		dialogue.potentialBubbles.put("check key", new SpeechBubble(world, this,
		new Criteria[]{new HasItem(world, new Key(world), world.player), new True(world)},
		new String[]{"Good.", "Uhh... you missed it. Try again. Remember, click on the key to add it to your inventory."}, 
		new String[]{"open door", "give key"}));
		
		dialogue.potentialBubbles.put("open door", new SpeechBubble(world, this,"Now to open the door, first open your inventory with the E key. Then, click on the key to grab it. Finally, click on the door with the key to use it.", "over"));
		
		dialogue.potentialBubbles.put("over", new SpeechBubble(world, this,"If you get confused, come talk to me again.", "end"));
		
		dialogues.add(dialogue);
		
		
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this, new Criteria[] {new Invert(world, new Said(world, dialogue, "what floor")), new True(world)}, new String[] {"By the way, you can also click on me to talk, even if there's no popup bubble.", "Didn't catch that?"}, new String[] {"what floor", "tell again"}));
		
		dialogue.potentialBubbles.put("what floor", new SpeechBubble(world, this,
				new Criteria[]{new HasItem(world, new Key(world), world.player), new True(world)},
				new String[]{"I see you already picked up the key. Good.", "Another locked door... But what's that on the floor?"}, 
				new String[]{"use key", "what floor answer"}));
		
		dialogue.potentialBubbles.put("what floor answer", new SpeechChoice(world, 
				new String[]{"I don't know.", "Dust.", "A key."}, 
				new String[]{"Uh... I don't know. What?", "Uhh... dust?", "A key."},
				new String[]{"idk", "dust","key"}));
		
		dialogue.potentialBubbles.put("idk", new SpeechBubble(world, this,"I'm talking about that key, right next to you.", "pick up"));
		
		dialogue.potentialBubbles.put("dust", new SpeechBubble(world, this,"No. Well... I mean... yes, but that's not what I was talking about. ", "idk"));
		
		dialogue.potentialBubbles.put("key", new SpeechBubble(world, this,"Yep! You have a good eye.", "pick up"));
		
		dialogue.potentialBubbles.put("tell again", new SpeechBubble(world, this,
				new Criteria[]{new HasItem(world, new Key(world), world.player), new True(world)},
				new String[]{"", ""}, 
				new String[]{"use key", "pick up"}));
		
		dialogue.potentialBubbles.put("pick up", new SpeechBubble(world, this,"Click on the key to grab it off the ground. Then open the door with it.", "end"));
		
		dialogue.potentialBubbles.put("use key", new SpeechBubble(world, this,"Open the door with the key.", "end"));
		
		dialogues.add(dialogue);
		
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"See how all of the doors in this hallway are lighter? That's because they're open.", "except"));
		
		dialogue.potentialBubbles.put("except", new SpeechBubble(world, this,"Except that door at the end. That one's locked.", "find key"));
		
		dialogue.potentialBubbles.put("find key", new SpeechBubble(world, this,"These are all storage rooms. There should be a key somewhere here... why don't you look for it?", "how to chest"));
		
		dialogue.potentialBubbles.put("how to chest", new SpeechBubble(world, this,"Left click on a container to open its inventory. If you open up your inventory, you can move items you find into it.", "feel free"));
		
		dialogue.potentialBubbles.put("feel free", new SpeechBubble(world, this,"Feel free to take anything you find.", "end"));
		
		dialogues.add(dialogue);
		
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this, new Criteria[] {new HasItem(world, new Key(world), world.player), new True(world)}, new String[] {"If you've got everthing you want, proceed through the door.", "Still looking for that key? I'm sure it's somewhere in here..."}, new String[] {"end", "end"}));
		
		dialogues.add(dialogue);
		
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this, new Criteria[] {new Invert(world, new Said(world, dialogue, "sword explain")), new True(world)}, new String[] {"Now, to learn to fight!", "Need to hear that again?"}, new String[] {"tell dummy", "sword explain"}));
		
		dialogue.potentialBubbles.put("tell dummy", new SpeechBubble(world, this,"There are three dummies in this room. One of them has a key inside.", "tell sword"));
		
		dialogue.potentialBubbles.put("tell sword", new SpeechBubble(world, this,"Here: take this sword and use it to uh... 'kill' these dummies.", "give sword"));
		
		invent = new Inventory(world, new int[][]{new int[]{0,0,0}}, 0, 0, true);
		invent.slot[0].item = new Sword(world, 1, 0, 0, 0);
		dialogue.potentialBubbles.put("give sword", new InvBubble(world, this, invent , "check sword"));
		
		dialogue.potentialBubbles.put("check sword", new SpeechBubble(world, this,
				new Criteria[]{new HasItem(world, new Sword(world, 1), world.player), new True(world)},
				new String[]{"Now to learn to use it.", "...Good try. Good try. Here, try again."}, 
				new String[]{"sword explain", "give sword"}));
		
		dialogue.potentialBubbles.put("sword explain", new SpeechBubble(world, this,"The first thing you need to do is equip the sword. Open up your inventory, then grab the sword and place it in one of the slots marked with a sword.", "sword explain 2"));
		
		dialogue.potentialBubbles.put("sword explain 2", new SpeechBubble(world, this,"To use the sword, you first have to enter fight mode. You can toggle fight mode with the SPACE BAR. When in fight mode, you will be able to use any equiped weapons with the left mouse button for your left hand and right mouse button for your right hand. However, you will not be able to open your inventory or interact with objects while in fight mode.", "end"));
		
		dialogues.add(dialogue);
		
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this, new Criteria[] {new Invert(world, new Said(world, dialogue, "lantern explain")), new True(world)}, new String[] {"Looks like the lights are out in here...", "What me to tell you that again?"}, new String[] {"tell lantern", "lantern explain"}));
		
		dialogue.potentialBubbles.put("tell lantern", new SpeechBubble(world, this,"Take this lantern to get through this room.", "give lantern"));
		
		invent = new Inventory(world, new int[][]{new int[]{0,0,0}}, 0, 0, true);
		invent.slot[0].item = new Lantern(world);
		dialogue.potentialBubbles.put("give lantern", new InvBubble(world, this, invent , "check lantern"));
		
		dialogue.potentialBubbles.put("check lantern", new SpeechBubble(world, this,
				new Criteria[]{new HasItem(world, new Lantern(world), world.player), new True(world)},
				new String[]{"", "Like, seriously. Take the lantern."}, 
				new String[]{"lantern explain", "give lantern"}));
		
		dialogue.potentialBubbles.put("lantern explain", new SpeechBubble(world, this,"The lantern is a handheld item, so you can equip it just like a sword. However, you don't need to enter fight mode to use it.", "goodbye"));
		
		dialogue.potentialBubbles.put("goodbye", new SpeechBubble(world, this,"See you on the other side.\u200B\u200B\u200B\u200B\u200B\u200B\u200B\u200B\u200B\u200B\u200B\u200B\u200B\u200B Of the room.", "end"));
		
		dialogues.add(dialogue);
		
		dialogue = new Dialogue(world, this);
		
		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"Well done. You made it.", "exit"));
		
		dialogue.potentialBubbles.put("exit", new SpeechBubble(world, this,"You're almost a proper adventurer. Just go through the door and around the corner.", "ignore"));
		
		dialogue.potentialBubbles.put("ignore", new SpeechBubble(world, this,"Uh... just ignore the rooms in the hall. Nothing of interest.", "end"));
		
		dialogues.add(dialogue);
		
		dialogue = new Dialogue(world, this);

		dialogue.potentialBubbles.put("start", new SpeechBubble(world, this,"Hmm... Looks like he got out.", "nice"));
		
		dialogue.potentialBubbles.put("nice", new SpeechBubble(world, this,"But you handled it! I didn't realize he'd put that door through so much...", "tell potion"));

		dialogue.potentialBubbles.put("tell potion", new SpeechBubble(world, this,"Here, have this heath potion to heal up.", "give potion"));
		
		invent = new Inventory(world, new int[][]{new int[]{0,0,0}}, 0, 0, true);
		invent.slot[0].item = new LifePotion(world);
		dialogue.potentialBubbles.put("give potion", new InvBubble(world, this, invent , "check potion"));
		
		dialogue.potentialBubbles.put("check potion", new SpeechBubble(world, this,
				new Criteria[]{new HasItem(world, new Lantern(world), world.player), new True(world)},
				new String[]{"You can use the potion in a couble of ways: Middle clicking on it in your inventor. Or moving it to one of the consumable slots in your inventory, marked with a potion, and then clicking on it, or pressing the corresponding number key.", "So uh... you don't want the potion? Okay."}, 
				new String[]{"sorry", "sorry"}));
		
		dialogue.potentialBubbles.put("sorry", new SpeechBubble(world, this,"So sorry about that. But you've clearly proven yourself competent. You are truely an adventurer.", "exit"));
		
		dialogue.potentialBubbles.put("exit", new SpeechBubble(world, this,"Proceed to the left through the door to begin your journy!", "end"));
		
		dialogues.add(dialogue);
		
		dialogue = dialogues.get(0);
	}

	@Override
	public void calc() {
		if(stage == 0) showPopupBubble("Hello. Click on this to talk to me!");
		else if(stage == 1) showPopupBubble("Good job.");
		else if(stage == 2 || stage == 3 || stage == 4 || stage == 5 || stage == 7) showPopupBubble("");
		else if(stage == 6) showPopupBubble("Hey! Over Here!");
		
		if(stage == 0 && world.player.y > 53*Tile.TS) {
			stage = 1;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			x = 30*Tile.TS + Tile.TS/2;
			y = 62*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(1);
		}
		else if(stage == 1 && world.player.y > 63*Tile.TS) {
			stage = 2;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			x = 30*Tile.TS + Tile.TS/2;
			y = 65*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(2);
		}
		else if(stage==2 && world.player.x > 45*Tile.TS) {
			stage = 3;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			x = 56*Tile.TS + Tile.TS/2;
			y = 65*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(3);
		}
		else if(stage==3 && world.player.x > 57*Tile.TS) {
			stage = 4;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			x = 58*Tile.TS + Tile.TS/2;
			y = 65*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(4);
		}
		else if(stage==4 && world.player.y < 59*Tile.TS) {
			stage = 5;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			x = 64*Tile.TS + Tile.TS/2;
			y = 58*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(5);
		}
		else if(stage==5 && world.player.y < 45*Tile.TS && world.player.inv.contains(new Lantern(world)) != null) {
			stage = 6;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			x = 66*Tile.TS + Tile.TS/2;
			y = 40*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(6);
		}
		else if(stage==6){
			int numFound = 0;
			for(Entity e: world.entities){
				if(e instanceof TutorialGoon)numFound++;
			}
			if(numFound == 1){
				stage = 7;
				for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
				x = 66*Tile.TS + Tile.TS/2;
				y = 20*Tile.TS + Tile.TS/2;
				for(int i = 0; i < 200; i++)world.entities.add(Poof.get(world, x, y));
				speechBubble.dismissed = false;
				dialogue = dialogues.get(7);
			}
		}
		
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
