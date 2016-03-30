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
	public void calc() {}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(/*Texture*/ sprite,/*x*/ x-origin_x+d_offx,/*y*/ y-origin_y+d_offy,/*originX*/origin_x,/*originY*/origin_y,/*width*/ d_width,/*height*/ d_height,/*scaleX*/1,/*scaleY*/1,/*rotation*/angle,/*uselss shit to the right*/0,0,sprite.getWidth(),sprite.getHeight(),flipX,flipY);
		
		portraitBackground.draw(batch);
		
		portrait.draw(batch);
	}
	
	@Override
	public void post() {}

}
