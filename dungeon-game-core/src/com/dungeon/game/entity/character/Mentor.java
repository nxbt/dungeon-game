package com.dungeon.game.entity.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.dungeon.game.entity.hud.dialogue.Dialogue;
import com.dungeon.game.entity.hud.dialogue.SpeechBubble;
import com.dungeon.game.entity.hud.dialogue.SpeechChoice;
import com.dungeon.game.world.Tile;
import com.dungeon.game.world.World;

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
		/*00*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Greetings!",1));
		/*01*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"I have been informed that you are here to learn the craft of combat?",2));
		/*02*/dialogue.potentialBubbles.add(new SpeechChoice(world,new String[]{"Yes","No","Depends"},new String[]{"That would be the plan!", "Actaully, I was hoping to avoid the whole stabing bit - can we just cut to the part where I save the world and walk home a hero?", "That depends - on whether or not you're a good teacher."},new int[]{3,20,22}));
		/*03*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Let's get to it - where would you like to start? With the bow, the blade, or the band? Err... wand?",4));
		/*04*/dialogue.potentialBubbles.add(new SpeechChoice(world,new String[]{"Bow","Blade","Wand","Band"},new String[]{"How about the pew pew pew?", "Stabbing has always been my forte...", "Magic should do, I think!", "I'm horridly tempted by the band."},new int[]{5,10,14,18}));
		/*05*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Bow it is then. Easy to learn, hard to master. As I like to say...",6));
		/*06*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Arrows are a pain to learn - but a bigger pain for your enemies!",7));
		/*07*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Make sure you have an arrow on you, then knoch it with [Right Mouse], [Hold Right Mouse] to pull back, and [Release Right Mouse] to fire! \n Go on give it a try and come back when you've had enough.",8));
		/*08*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Back already? Wan't to move on?",9));
		/*09*/dialogue.potentialBubbles.add(new SpeechChoice(world,new String[]{"Yes","No"},new String[]{"Onward! What's next?", "Let me figure this out..."},new int[]{24,7}));
		/*10*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Ahh... great minds and mine think alike! The easiest and most effective of weapons!",11));
		/*11*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Just [Point] and [Click Right Mouse] to swing!",12));
		/*12*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Back already? Wan't to move on?",13));
		/*13*/dialogue.potentialBubbles.add(new SpeechChoice(world,new String[]{"Yes","No"},new String[]{"Onward! What's next?", "Let me figure this out..."},new int[]{24,11}));
		/*14*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"The wand... channel power against your oponents by sheer foce of will. The most frightening and aweful art.",15));
		/*15*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Using the wand, [Click Left Mouse] to shoot a fireball!",16));
		/*16*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Back already? Wan't to move on?",17));
		/*17*/dialogue.potentialBubbles.add(new SpeechChoice(world,new String[]{"Yes","No"},new String[]{"Onward! What's next?", "Let me figure this out..."},new int[]{24,15}));
		/*18*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Yes... very funny.",19));
		/*19*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"But seriously...",24));
		/*20*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"In this life we are offered many choices... but sometimes we are not. And so we must follow our path to its ending, whatever ending that may be.",21));
		/*21*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"I'm affraid this is one of those times...",3));
		/*22*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"I'm terribly offended! You'd doubt the master? Then again, I couldn't expect you to know that I am, indeed, the Master - but I AM!\u200B\u200B\u200B\u200B I AM!",23));
		/*23*/dialogue.potentialBubbles.add(new SpeechChoice(world,new String[]{"Yes Master...","Yes Master!","Yes Master?"},new String[]{"Yes Master...","Yes Master!","Yes Master?"},new int[]{3,3,3}));
		/*24*/dialogue.potentialBubbles.add(new SpeechBubble(world,this,3,"Let's continue with your training. What next: the bow, the blade, or the wand?",25));
		/*25*/dialogue.potentialBubbles.add(new SpeechChoice(world,new String[]{"Bow","Blade","Wand"},new String[]{"How about the pew pew pew?", "Stabbing has always been my forte...", "Magic should do, I think!"},new int[]{5,10,14}));





		
		
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
