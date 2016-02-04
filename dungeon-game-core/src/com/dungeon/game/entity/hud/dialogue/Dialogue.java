package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.world.World;

public class Dialogue extends Hud {
	
	private World world;
	
	private ArrayList<SpeechBubble> speechBubbles;
	
	private ArrayList<Character> characters;
	
	public Dialogue(World world, Character character){
		super(null,0,0);
		this.world = world;
		speechBubbles = new ArrayList<SpeechBubble>();
		characters = new ArrayList<Character>();
		characters.add(character);
		characters.add(world.player);
		speechBubbles.add(new SpeechBubble(world,character,5,"Hello There! How you doin'?"));
		speechBubbles.add(new SpeechBubble(world,world.player,5,"How kind of you to ask. I'm doing fine!"));
		speechBubbles.add(new SpeechBubble(world,character,5,"Ha Ha, nice."));
		speechBubbles.add(new SpeechBubble(world,world.player,5,"I hate you"));
		speechBubbles.add(new SpeechBubble(world,character,5,"Good one pall!"));
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
	
	public boolean done() {
		return speechBubbles.size() == 0 || speechBubbles.get(0).done();
	}
	
	public void open() {
		if(!world.hudEntities.contains(this))world.hudEntities.add(0,this);
	}
	
	public void close() {
		world.hudEntities.remove(this);
	}
	
	public void draw(SpriteBatch batch){
		for(SpeechBubble bubble: speechBubbles){
			if((bubble.character.equals(characters.get(0))||bubble.character.equals(characters.get(1)))&&bubble.y+bubble.d_height<world.cam.HEIGHT)bubble.draw(batch);
		}
	}

	@Override
	public void post() {}
}
