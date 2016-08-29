package com.dungeon.game.entity.hud.dialogue;
//NOTE THIS CRASHES
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.world.World;

public class EmptyBubble extends SpeechBubble {

	public EmptyBubble(World world, Character character, int width, int height) {
		super(world, character);
		dWidth = width;
		dHeight = height;
	}

}
