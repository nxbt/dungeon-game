package com.dungeon.game.entity.hud;

import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.World;

public class InvButton extends Button {

	public InvButton(World world, float x, float y) {
		super(world, x, y);
		
		sprite = new Texture("invButton.png");
		
		this.d_width = sprite.getWidth();
		this.d_height = sprite.getHeight();
	}
	
	@Override
	public void click() {
		if(world.hudEntities.contains(world.player.inv.graphic)) world.player.inv.graphic.close();
		else if(!world.player.fightMode)world.player.inv.graphic.open();
	}

	@Override
	public void calc() {
		
	}

	@Override
	public void post() {
		
	}

}
