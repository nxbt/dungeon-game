package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class DialogueHud extends Hud {

	public DialogueHud(World world, float x, float y) {
		super(world, x, y);
		
		sprite = new Texture("dialogueHud.png");
	}

	@Override
	public void init() {}

	@Override
	public void calc() {}

	@Override
	public void post() {}

}
