package com.dungeon.game.entity.hud.dialogue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.game.entity.character.Character;
import com.dungeon.game.inventory.Inventory;
import com.dungeon.game.inventory.Slot;
import com.dungeon.game.world.World;

public class InvBubble extends SpeechChoice {

	public Inventory inv;
	public InvDisplayBubble bubble;
	
	public InvBubble(World world, Character character, Inventory inv, String key) {
		super(world, new String[]{"Thanks!"},new String[]{"Thank you!"},new String[]{key});
		this.inv = inv;
		int width = 0;
		int height = 0;
		bubble = new InvDisplayBubble(world, character, inv);
	}

}
