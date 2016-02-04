package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.world.World;

public class Dialogue extends Hud {
	
	private static final Texture PORTRAIT_BACKGROUND = new Texture("portraitBackground.png");
	
	private World world;
	
	private ArrayList<SpeechBubble> speechBubbles;
	
	private ArrayList<SpeechBubble> potentialBubbles;
	
	private ArrayList<Character> characters;
	
	private int index;
	
	public Dialogue(World world, Character character){
		super(null,0,0);
		this.world = world;
		speechBubbles = new ArrayList<SpeechBubble>();
		potentialBubbles = new ArrayList<SpeechBubble>();
		characters = new ArrayList<Character>();
		characters.add(character);
		characters.add(world.player);
		potentialBubbles.add(new SpeechBubble(world,character,3,"Hello There! How you doin'?",1));
		potentialBubbles.add(new SpeechBubble(world,world.player,3,"I'm fly m8",0));
		speechBubbles.add((SpeechBubble) potentialBubbles.get(0).clone());
	}
	
	public void calc(){
		if(done()&&!(speechBubbles.get(0).proceedIndex>=potentialBubbles.size())){
			speechBubbles.add(0,(SpeechBubble) potentialBubbles.get(speechBubbles.get(0).proceedIndex).clone());
		}
		if(world.player.fightMode)close();
		int heightCounter = 88;
		for(SpeechBubble bubble: speechBubbles){
			if(!bubble.done())bubble.update();
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
			if((bubble.character.equals(characters.get(0))||bubble.character.equals(characters.get(1)))&&bubble.y<world.cam.HEIGHT)bubble.draw(batch);
		}
		batch.draw(PORTRAIT_BACKGROUND,4,4,PORTRAIT_BACKGROUND.getWidth(),PORTRAIT_BACKGROUND.getHeight());
		batch.draw(PORTRAIT_BACKGROUND,world.cam.WIDTH-72,4,PORTRAIT_BACKGROUND.getWidth(),PORTRAIT_BACKGROUND.getHeight());
		batch.draw(characters.get(0).face,4,4,characters.get(0).face.getWidth(),characters.get(0).face.getHeight());
		batch.draw(characters.get(1).face,world.cam.WIDTH-72,4,characters.get(0).face.getWidth(),characters.get(0).face.getHeight());
	}

	@Override
	public void post() {}
}
