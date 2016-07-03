package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class DialogueHud extends Hud {
	private Portrait portrait;
	
	private PortraitBackground portraitBackground;
	
	public DialogueHud(World world, Character character) {
		super(world, 0, 0, 80, 80, "dialogueHud.png");
		
		portrait = new Portrait(world, 4, 4);
		
		portraitBackground = new PortraitBackground(world, 4, 4);
	}

	@Override
	public void calc() {
		super.calc();
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		
		portraitBackground.draw(batch);
		
		portrait.draw(batch);
	}
	
	@Override
	public void post() {}

}
