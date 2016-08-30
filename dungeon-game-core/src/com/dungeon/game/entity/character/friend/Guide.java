package com.dungeon.game.entity.character.friend;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.criteria.Criteria;
import com.dungeon.game.criteria.HasItem;
import com.dungeon.game.criteria.Invert;
import com.dungeon.game.criteria.Said;
import com.dungeon.game.criteria.True;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.InvBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
import com.dungeon.game.entity.particle.Poof;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.item.Key;
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
		
//		dialogue.potentialBubbles.put("what floor", new SpeechBubble(world, this,"Another locked door... But what's that on the floor?", "what floor answer"));
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
		
		dialogue = dialogues.get(0);
	}

	@Override
	public void calc() {
		if(stage == 0) showPopupBubble("Hello. Click on this to talk to me!");
		else if(stage == 1) showPopupBubble("Good job.");
		else if(stage == 2) showPopupBubble("");
		
		if(stage == 0 && world.player.y > 53*Tile.TS) {
			stage = 1;
			for(int i = 0; i < 20; i++)world.entities.add(new Poof(world, x, y));
			x = 30*Tile.TS + Tile.TS/2;
			y = 62*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 20; i++)world.entities.add(new Poof(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(1);
		}
		else if(stage == 1 && world.player.y > 63*Tile.TS) {
			stage = 2;
			for(int i = 0; i < 20; i++)world.entities.add(new Poof(world, x, y));
			x = 30*Tile.TS + Tile.TS/2;
			y = 65*Tile.TS + Tile.TS/2;
			for(int i = 0; i < 20; i++)world.entities.add(new Poof(world, x, y));
			speechBubble.dismissed = false;
			dialogue = dialogues.get(2);
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
