package com.dungeon.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.dungeon.game.world.Floor;

public class Player extends Dynamic {
	public Player(int x, int y) {
		super(x, y);
	}
	
	public void init() {
		name = "Player";
		
		acel = 1.5f;
		mvel = 5;
		fric = 1;
		
		width = 26;
		height = 26;
		
		d_width = 32;
		d_height = 32;
		
		d_offx = -3;
		d_offy = -3;
		
		sprite = new Texture("Player.png");
		
		solid = true;
	}
	
	public void calc(Floor floor) {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) inp_lt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) inp_rt = true;
		if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) inp_up = true;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) inp_dn = true;
	}
}