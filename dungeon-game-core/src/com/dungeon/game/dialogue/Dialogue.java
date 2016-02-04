package com.dungeon.game.dialogue;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.world.World;
import com.dungeon.game.entity.Character;
import com.dungeon.game.entity.Mentor;

public class Dialogue extends Hud {
	
	private World world;
	
	private ArrayList<SpeechBubble> speechBubbles;
	
	private ArrayList<Character> characters;
	
	public Dialogue(World world){
		super(null,0,0);
		this.world = world;
		speechBubbles = new ArrayList<SpeechBubble>();
		characters = new ArrayList<Character>();
		Character temp = new Mentor(world, 0, 0);
		characters.add(temp);
		characters.add(world.player);
		speechBubbles.add(new SpeechBubble(world,temp,5,"Hello There! How you doing?"));
		speechBubbles.add(new SpeechBubble(world,world.player,5,"How kind of you to ask. Fuck You Bitch."));
		speechBubbles.add(new SpeechBubble(world,temp,5,"Ha Ha, hello friend."));
		speechBubbles.add(new SpeechBubble(world,world.player,5,"Fuck. You. Bitch."));
		speechBubbles.add(new SpeechBubble(world,temp,5,"Good one pall!"));
		speechBubbles.add(new SpeechBubble(world,world.player,5,"I'll abreviate. FUB"));
	}
	
	public void calc(){
		int heightCounter = 0;
		for(SpeechBubble bubble: speechBubbles){
			bubble.update();
			bubble.y = heightCounter;
			heightCounter+=bubble.d_height+8;
			if(bubble.character.equals(characters.get(0)))bubble.x = 8;
			else if(bubble.character.equals(characters.get(1)))bubble.x = world.cam.WIDTH-bubble.d_width-8;
		}
	}
	
	public void draw(SpriteBatch batch){
		for(SpeechBubble bubble: speechBubbles){
			if((bubble.character.equals(characters.get(0))||bubble.character.equals(characters.get(1)))&&bubble.y+bubble.d_height<world.cam.HEIGHT)bubble.draw(batch);
		}
		
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub
		
	}
}
