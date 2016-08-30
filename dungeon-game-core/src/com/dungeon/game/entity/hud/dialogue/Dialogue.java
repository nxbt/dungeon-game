package com.dungeon.game.entity.hud.dialogue;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.entity.hud.Hud;
import com.dungeon.game.item.Item;
import com.dungeon.game.world.World;

public class Dialogue extends Hud {
	
	private static final Texture PORTRAIT_BACKGROUND = new Texture("portraitBackground.png");
	
	protected final NinePatch SPEECH_BUBBLE_CONNECTOR = new NinePatch(new Texture("hudBubbleConnector.png"), 4, 4, 0, 0);
	protected final NinePatch SPEECH_BUBBLE_START0 = new NinePatch(new Texture("hudBubbleStart0.png"), 20, 4, 0, 17);
	protected final NinePatch SPEECH_BUBBLE_START1 = new NinePatch(new Texture("hudBubbleStart1.png"), 20, 4, 0, 17);
	
	public ArrayList<SpeechBubble> speechBubbles;
	
	public HashMap<String, SpeechBubble> potentialBubbles;
	
	private ArrayList<Character> characters;
	
	public Dialogue(World world, Character character){
		super(world,0,0, Item.SIZE, Item.SIZE, "slot.png");
		speechBubbles = new ArrayList<SpeechBubble>();
		potentialBubbles = new HashMap<String, SpeechBubble>();
		characters = new ArrayList<Character>();
		characters.add(character);
		characters.add(world.player);
		dWidth = world.cam.width;
		dHeight = world.cam.height;
	}
	
	public void calc(){
		if(world.hudEntities.indexOf(this) != 0) {
			world.hudEntities.remove(this);
			world.hudEntities.add(0,this);
		}
		
		if(world.player.fightMode) close();
		int heightCounter = 88;
		for(int i = 0; i < speechBubbles.size(); i++){
			SpeechBubble bubble = speechBubbles.get(i);
			
			bubble.y = heightCounter;
			heightCounter+=bubble.dHeight+8;
			if(bubble.character.equals(characters.get(0)))bubble.x = 8;
			else if(bubble.character.equals(characters.get(1)))bubble.x = world.cam.width-bubble.dWidth-8;
			if(!bubble.done())bubble.update();
			
			if(bubble.endText != null && bubble.endText.equals("")) {
				speechBubbles.add(0,(SpeechBubble) potentialBubbles.get(speechBubbles.get(0).getProceedKey()).clone());
				speechBubbles.remove(1);
			}
		}
		if(speechBubbles.get(0) instanceof InvBubble && ((InvBubble)speechBubbles.get(0)).madeChoice) {
			speechBubbles.remove(1);
		}
	}
	
	public void hovered() {
		if(speechBubbles.size() > 0 && done()&&(world.mouse.lb_pressed || speechBubbles.get(0) instanceof SpeechChoice)){
			if(speechBubbles.get(0) instanceof SpeechChoice){
				speechBubbles.add(0, ((SpeechChoice)speechBubbles.get(0)).getChoiceBubble());
				speechBubbles.remove(1);
			}
			else if(speechBubbles.get(0).getProceedKey().equals("end")) close();
			else {
				speechBubbles.add(0,(SpeechBubble) potentialBubbles.get(speechBubbles.get(0).getProceedKey()).clone());
				
				if(speechBubbles.get(0)instanceof InvBubble){
					speechBubbles.add(1, ((InvBubble)speechBubbles.get(0)).bubble);
				}
			}
		}
	}
	
	public boolean done() {
		return speechBubbles.size() == 0 || speechBubbles.get(0).done();
	}
	
	public void open() {
		if(!world.hudEntities.contains(this)) world.hudEntities.add(0,this);
		
		world.player.actionState[1] = true;
		
		speechBubbles.clear();
		speechBubbles.add((SpeechBubble) potentialBubbles.get("start").clone());
	}
	
	public void close() {
		world.hudEntities.remove(this);
		
		world.player.actionState[1] = false;
		world.player.focusedEntity = world.mouse;
	}
	
	public void draw(SpriteBatch batch){
		batch.setColor(1,1,1,0.8f);
		for(SpeechBubble bubble: speechBubbles){
			if((bubble.character.equals(characters.get(0))||bubble.character.equals(characters.get(1)))&&bubble.y<world.cam.height)bubble.draw(batch);
		}
		batch.draw(PORTRAIT_BACKGROUND,4,4,PORTRAIT_BACKGROUND.getWidth(),PORTRAIT_BACKGROUND.getHeight());
		batch.draw(PORTRAIT_BACKGROUND,world.cam.width-72,4,PORTRAIT_BACKGROUND.getWidth(),PORTRAIT_BACKGROUND.getHeight());
		batch.draw(characters.get(0).face,4,4,characters.get(0).face.getWidth(),characters.get(0).face.getHeight());
		batch.draw(characters.get(1).face,world.cam.width-72,4,characters.get(0).face.getWidth(),characters.get(0).face.getHeight());
		float char0ConnectorStart = 60;
		float char1ConnectorStart = 60;
		boolean madeChar0Start = false;
		boolean madeChar1Start = false;
		for(SpeechBubble bubble: speechBubbles){
			if(bubble.character.equals(characters.get(0))){
				if(madeChar0Start)SPEECH_BUBBLE_CONNECTOR.draw(batch, bubble.x+4, char0ConnectorStart-4, 16, bubble.y-char0ConnectorStart+8);
				else{
					SPEECH_BUBBLE_START0.draw(batch, bubble.x+4, char0ConnectorStart-4, 32, bubble.y-char0ConnectorStart+8);
					madeChar0Start = true;
				}
				char0ConnectorStart = bubble.y+bubble.dHeight;
			}
			
			if(bubble.character.equals(characters.get(1))){
				if(madeChar1Start)SPEECH_BUBBLE_CONNECTOR.draw(batch, bubble.x+bubble.dWidth-20, char1ConnectorStart-4, 16, bubble.y-char1ConnectorStart+8);
				else{
					SPEECH_BUBBLE_START1.draw(batch, bubble.x+bubble.dWidth-36, char1ConnectorStart-4, 32, bubble.y-char1ConnectorStart+8);
					madeChar1Start = true;
				}
				char1ConnectorStart = bubble.y+bubble.dHeight;
			}
		}

		batch.setColor(1,1,1,1);
	}

	@Override
	public void post() {}
}
